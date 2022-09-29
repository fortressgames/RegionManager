package net.fortressgames.regionmanager.commands.subcommands;

import net.fortressgames.fortressapi.players.FortressPlayer;
import net.fortressgames.regionmanager.RegionLang;
import net.fortressgames.regionmanager.regions.Region;

public abstract class RegionMembers {

	public static void execute(FortressPlayer player, Region region, String[] args) {

		if(args.length == 4) {

			switch (args[2].toLowerCase()) {
				case "add" -> {

					if(region.getMembers().contains(args[3])) {
						player.sendMessage(RegionLang.ALREADY_MEMBER);
						return;
					}

					region.getMembers().add(args[3]);
					player.sendMessage(RegionLang.MEMBER_ADD);
				}

				case "remove" -> {
					region.getMembers().remove(args[3]);
					player.sendMessage(RegionLang.MEMBER_REMOVE);
				}
			}

			region.save();
		}
	}
}