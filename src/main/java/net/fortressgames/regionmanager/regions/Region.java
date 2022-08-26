package net.fortressgames.regionmanager.regions;

import lombok.Getter;
import lombok.Setter;
import net.fortressgames.regionmanager.RegionManager;
import net.fortressgames.regionmanager.utils.RegionMaths;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;

import java.util.List;

//todo get all blocks as list
//todo particles
public class Region {

	@Getter private final String name;
	@Setter @Getter private String displayName;
	@Setter	@Getter	private int pri;
	@Getter private final RegionMaths regionMaths;

	@Getter private final World world;

	@Getter private final List<String> flags;

	public Region(String name, int pri, RegionMaths regionMaths, World world, List<String> flags, String displayName) {
		this.world = world;

		this.name = name;
		this.displayName = displayName;
		this.pri = pri;
		this.regionMaths = regionMaths;
		this.flags = flags;
	}

	public void save() {
		Configuration config = RegionManager.getInstance().getConfig();

		config.set("Region." + name + ".Pri", this.pri);
		config.set("Region." + name + ".NumberOfPoints", regionMaths.getPoints().size());
		config.set("Region." + name + ".World", world.getName());

		for(int i = 0; i < regionMaths.getPoints().size(); i++) {

			config.set("Region." + name + ".Pos" + i, regionMaths.getPoints().get(i).toString());
		}

		config.set("Region." + name + ".MaxY", regionMaths.getMaxY());
		config.set("Region." + name + ".MinY", regionMaths.getMinY());

		config.set("Region." + name + ".Flags", flags);
		config.set("Region." + name + ".DisplayName", this.displayName);

		RegionManager.getInstance().saveConfig();
	}

	/**
	 * Example PVP, EFFECT_JUMP, TITLE
	 */
	public void addFlag(String flag, String fullFlag) {
		removeFlag(flag);
		flags.add(fullFlag);
	}

	/**
	 * Example PVP, EFFECT_JUMP, TITLE
	 */
	public void removeFlag(String flag) {
		int number = 0;

		for(String f : flags) {
			if(f.contains(flag)) {
				flags.remove(number);
				break;
			}

			number++;
		}
	}
}