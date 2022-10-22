package net.fortressgames.regionmanager.commands.subcommands;

import net.fortressgames.fortressapi.utils.Vector3;
import net.fortressgames.regionmanager.RegionLang;
import net.fortressgames.regionmanager.RegionManager;
import net.fortressgames.regionmanager.regions.Region;
import net.fortressgames.regionmanager.utils.RegionMaths;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public abstract class RegionShow {

	public static void execute(Player player, Region region, String[] args) {

		if(args.length >= 2) {
			for(int y = region.getRegionMaths().getMinY(); y < region.getRegionMaths().getMaxY(); y++) {

				int finalY = y;

				Bukkit.getScheduler().scheduleSyncDelayedTask(RegionManager.getInstance(), () -> {

					for(int i = 0; i < region.getRegionMaths().getPoints().size(); i++) {

						RegionMaths regionMaths = region.getRegionMaths();
						int next = i+1;

						if(regionMaths.getPoints().size() == next) {
							next = 0;
						}

						Vector3 p1 = new Vector3(regionMaths.getPoints().get(i).getBlockX(), region.getRegionMaths().getMaxY(), regionMaths.getPoints().get(i).getBlockZ());
						Vector3 p2 = new Vector3(regionMaths.getPoints().get(next).getBlockX(), region.getRegionMaths().getMaxY(), regionMaths.getPoints().get(next).getBlockZ());

						int number = (int)p1.toLocation(player.getWorld()).distance(p2.toLocation(player.getWorld()));

						Vector point1 = new Vector(p1.getBlockX(), p1.getBlockY(), p1.getBlockZ());
						Vector vector = new Vector(p2.getBlockX(), p2.getBlockY(), p2.getBlockZ()).subtract(point1).normalize().multiply(1);

						double covered = 0;

						for(; covered < number; point1.add(vector)) {

							Location location = point1.toLocation(player.getWorld());

							player.spawnParticle(Particle.END_ROD, location.getX() + 0.5, finalY, location.getZ() + 0.5, 1, 0, 0, 0, 0);

							covered += 1;
						}
					}

				}, (y - region.getRegionMaths().getMinY()) + 1);
			}

			player.sendMessage(RegionLang.REGION_SHOW);
		}
	}
}