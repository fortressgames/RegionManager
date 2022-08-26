package net.fortressgames.regionmanager.regions;

import lombok.Getter;
import net.fortressgames.fortressapi.utils.Vector2;
import net.fortressgames.regionmanager.RegionManager;
import net.fortressgames.regionmanager.users.UserModule;
import net.fortressgames.regionmanager.utils.RegionMaths;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegionModule {

	private static RegionModule instance;
	private final HashMap<String, Region> regions = new HashMap<>();
	@Getter private Region global;

	public void addRegion(String name, int pri, RegionMaths regionMaths, World world, List<String> flags, String displayName) {
		this.regions.put(name, new Region(name, pri, regionMaths, world, flags, displayName));
	}

	public void addRegion(String name, RegionMaths regionMaths, World world) {
		this.regions.put(name, new Region(name, 0, regionMaths, world, new ArrayList<>(), name));
		this.regions.get(name).save();
	}

	public void removeRegion(String region) {
		this.regions.remove(region);

		UserModule.getInstance().getAllUsers().forEach(user -> user.getRegions().removeIf(rg -> rg.getName().equals(region)));
	}

	public Region getRegion(String region) {
		return this.regions.get(region);
	}

	public boolean isRegion(String value) {
		return RegionModule.getInstance().getRegion(value) != null;
	}

	public List<Region> getAll() {
		return new ArrayList<>(this.regions.values());
	}

	public void loadRegions() {
		FileConfiguration file = RegionManager.getInstance().getConfig();

		for(String region : file.getKeys(true)) {

			if(StringUtils.countMatches(region, ".") == 1) {

				List<Vector2> points = new ArrayList<>();
				for(int i = 0; i < file.getInt(region + ".NumberOfPoints"); i++) {
					String[] split = file.getString(region + ".Pos" + i).split(",");

					points.add(new Vector2(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
				}

				RegionModule.getInstance().addRegion(
						region.split("\\.")[1],
						file.getInt(region + ".Pri"),

						new RegionMaths(points, file.getInt(region + ".MaxY"), file.getInt(region + ".MinY")),

						Bukkit.getWorld(file.getString(region + ".World")), file.getStringList(region + ".Flags"),
						file.getString(region + ".DisplayName")
				);
			}
		}

		// Load global region
		if(!regions.containsKey("global")) {
			addRegion("global", new RegionMaths(new ArrayList<>(), 0, 0), Bukkit.getWorlds().get(0));
		}

		this.global = getRegion("global");
	}

	public static RegionModule getInstance() {
		if(instance == null) {
			instance = new RegionModule();
		}

		return instance;
	}
}