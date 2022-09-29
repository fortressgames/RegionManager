package net.fortressgames.regionmanager.regions;

import lombok.Getter;
import net.fortressgames.fortressapi.utils.Config;
import net.fortressgames.fortressapi.utils.Vector2;
import net.fortressgames.regionmanager.RegionManager;
import net.fortressgames.regionmanager.users.UserModule;
import net.fortressgames.regionmanager.utils.RegionMaths;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RegionModule {

	private static RegionModule instance;
	private final HashMap<String, Region> regions = new HashMap<>();
	@Getter private Region global;

	public void addRegion(String name, int pri, RegionMaths regionMaths, World world, List<String> flags, String displayName, List<String> members) {
		this.regions.put(name, new Region(name, pri, regionMaths, world, flags, displayName, members));
		this.regions.get(name).save();
	}

	public void addRegion(String name, RegionMaths regionMaths, World world) {
		this.regions.put(name, new Region(name, 0, regionMaths, world, new ArrayList<>(), name, new ArrayList<>()));
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

	public List<String> getAllByName() {
		List<String> out = new ArrayList<>();
		this.regions.forEach((s, region) -> out.add(region.getName()));
		return out;
	}

	public List<String> getFlags() {
		return Arrays.asList("pvp", "title", "action_bar", "sound", "effect", "particle", "entry");
	}

	public void loadRegions() {
		regions.clear();

		for(File file : new File(RegionManager.getInstance().getDataFolder() + "/Regions").listFiles()) {
			Config config = new Config(file);

			List<Vector2> points = new ArrayList<>();
			for(String pos : config.getConfig().getStringList("Pos")) {
				String[] split = pos.split(",");

				points.add(new Vector2(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
			}

			RegionModule.getInstance().addRegion(
					file.getName().replace(".yml", ""),
					config.getConfig().getInt("Pri"),

					new RegionMaths(points, config.getConfig().getInt("MaxY"), config.getConfig().getInt("MinY")),

					Bukkit.getWorld(config.getConfig().getString("World")), config.getConfig().getStringList("Flags"),
					config.getConfig().getString("DisplayName"),
					config.getConfig().getStringList("Members")
			);
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