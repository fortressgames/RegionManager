package net.fortressgames.regionmanager.commands.subcommands;

import net.fortressgames.regionmanager.RegionLang;
import net.fortressgames.regionmanager.regions.Region;
import org.bukkit.entity.Player;

public abstract class RegionFlag {

	public static void execute(Player player, Region region, String[] args) {

		if(args.length >= 5) {

			if(args[2].equalsIgnoreCase("add")) {

				switch(args[3].toUpperCase()) {

					case "PVP", "TITLE", "ACTION_BAR", "ENTRY" -> {
						try {
							region.addFlag(args[3].toUpperCase() + "_" + args[4].toUpperCase(),
									args[3].toUpperCase());

						} catch (ArrayIndexOutOfBoundsException ignored) {
							return;
						}
					}
					case "SOUND" -> {
						try {
							region.addFlag(args[3].toUpperCase() + "_" + args[4].toUpperCase(),
									args[3].toUpperCase() + "_" + args[4].toUpperCase());

						} catch (ArrayIndexOutOfBoundsException ignored) {
							return;
						}
					}

					case "EFFECT", "PARTICLE" -> {
						try {
							region.addFlag(args[3].toUpperCase() + "_" + args[4].toUpperCase() + "_" + args[5].toUpperCase(),
									args[3].toUpperCase() + "_" + args[4].toUpperCase());

						} catch (ArrayIndexOutOfBoundsException ignored) {
							return;
						}
					}
				}

				region.save();
				player.sendMessage(RegionLang.REGION_FLAG_ADDED);
				return;
			}
		}

		if(args.length >= 4 && args[2].equalsIgnoreCase("remove")) {

			region.removeFlag(args[3].toUpperCase());
			region.save();
			player.sendMessage(RegionLang.REGION_FLAG_REMOVED);
		}
	}
}