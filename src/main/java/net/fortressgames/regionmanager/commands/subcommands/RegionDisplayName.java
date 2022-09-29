package net.fortressgames.regionmanager.commands.subcommands;

import net.fortressgames.fortressapi.players.FortressPlayer;
import net.fortressgames.regionmanager.RegionLang;
import net.fortressgames.regionmanager.regions.Region;

public abstract class RegionDisplayName {

	public static void execute(FortressPlayer player, Region region, String[] args) {

		if(args.length >= 3) {
			StringBuilder stringBuilder = new StringBuilder();
			for(int i = 2; i < args.length; i++) {

				if(i == args.length -1) {
					stringBuilder.append(args[i]);
				} else {
					stringBuilder.append(args[i]).append(" ");
				}
			}

			region.setDisplayName(stringBuilder.toString());
			region.save();

			player.sendMessage(RegionLang.REGION_NAME_UPDATE);
		}
	}
}