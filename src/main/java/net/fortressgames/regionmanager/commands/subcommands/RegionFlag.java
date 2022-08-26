package net.fortressgames.regionmanager.commands.subcommands;

import net.fortressgames.fortressapi.players.FortressPlayer;
import net.fortressgames.regionmanager.RegionLang;
import net.fortressgames.regionmanager.regions.Region;

public abstract class RegionFlag {

	public static void execute(FortressPlayer player, Region region, String[] args) {

		if(args.length >= 5) {

			if(args[2].equalsIgnoreCase("add")) {

				switch(args[3].toUpperCase()) {

					case "PVP", "TITLE", "ACTION_BAR", "SOUND" -> {
						try {
							region.addFlag(args[3].toUpperCase(), args[3].toUpperCase() + "_" + args[4].toUpperCase());
						} catch (ArrayIndexOutOfBoundsException ignored) {
							return;
						}
					}
					case "EFFECT", "PARTICLE" -> {
						try {
							region.addFlag(args[3].toUpperCase(), args[3].toUpperCase() + "_" + args[4].toUpperCase() + "_" + args[5].toUpperCase());
						} catch (ArrayIndexOutOfBoundsException ignored) {
							return;
						}
					}
				}

				region.save();
				player.sendMessage(RegionLang.REGION_FLAG_ADDED);
				return;
			}

			if(args[2].equalsIgnoreCase("remove")) {

				region.removeFlag(args[3]);
				region.save();
				player.sendMessage(RegionLang.REGION_FLAG_REMOVED);
			}
		}
	}
}