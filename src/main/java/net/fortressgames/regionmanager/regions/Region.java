package net.fortressgames.regionmanager.regions;

import lombok.Getter;
import lombok.Setter;
import net.fortressgames.fortressapi.players.FortressPlayerModule;
import net.fortressgames.fortressapi.utils.Vector3;
import net.fortressgames.regionmanager.RegionManager;
import net.fortressgames.regionmanager.utils.RegionMaths;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Region {

	@Getter private final String name;
	@Setter @Getter private String displayName;
	@Setter	@Getter	private int pri;
	@Getter private final RegionMaths regionMaths;

	@Getter private final World world;

	@Getter private final List<String> flags;
	@Getter private final HashMap<String, DisplayParticleTask> particleTasks = new HashMap<>();

	@Getter private final List<Vector3> allPoints;

	public Region(String name, int pri, RegionMaths regionMaths, World world, List<String> flags, String displayName) {
		this.world = world;

		this.name = name;
		this.displayName = displayName;
		this.pri = pri;
		this.regionMaths = regionMaths;
		this.flags = flags;

		this.allPoints = regionMaths.getAllPoints();

		flags.forEach(flag -> {
			if(flag.contains("PARTICLE")) {
				particleStart(flag, flag.split("_")[0] + "_" + flag.split("_")[1]);
			}
		});
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

	private void particleStart(String flag, String old) {

		DisplayParticleTask task = new DisplayParticleTask(
				Particle.valueOf(flag.split("_")[1]),
				Integer.parseInt(flag.split("_")[2]),
				this);
		task.runTaskTimer(RegionManager.getInstance(), TimeUnit.SECONDS, 1);

		particleTasks.put(old, task);
	}

	/**
	 * Example PVP, EFFECT_JUMP, TITLE
	 */
	public void addFlag(String flag, String old) {
		removeFlag(old);
		flags.add(flag);

		if(flag.contains("PARTICLE")) {
			particleStart(flag, old);
		}

		if(flag.contains("EFFECT")) {
			FortressPlayerModule.getInstance().getOnlinePlayers().forEach(player -> {
				if(regionMaths.inside(new Vector3(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ()))) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(flag.split("_")[1]),
							Integer.MAX_VALUE, Integer.parseInt(flag.split("_")[2])));
				}
			});
		}
	}

	/**
	 * Example PVP, EFFECT_JUMP, TITLE
	 */
	public void removeFlag(String flag) {
		if(particleTasks.containsKey(flag)) {
			particleTasks.get(flag).cancel();
			particleTasks.remove(flag);
		}

		if(flag.contains("EFFECT")) {
			FortressPlayerModule.getInstance().getOnlinePlayers().forEach(player -> {
				if(regionMaths.inside(new Vector3(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ()))) {
					player.removePotionEffect(PotionEffectType.getByName(flag.split("_")[1]));
				}
			});
		}

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