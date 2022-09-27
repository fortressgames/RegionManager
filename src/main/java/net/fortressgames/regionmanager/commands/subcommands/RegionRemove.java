package net.fortressgames.regionmanager.commands.subcommands;

import net.fortressgames.fortressapi.players.FortressPlayer;
import net.fortressgames.regionmanager.RegionLang;
import net.fortressgames.regionmanager.RegionManager;
import net.fortressgames.regionmanager.regions.Region;
import net.fortressgames.regionmanager.regions.RegionModule;

public abstract class RegionRemove {

	public static void execute(FortressPlayer player, Region region, String[] args) {

		if(args.length == 2) {

			if(region.equals(RegionModule.getInstance().getGlobal())) return;

			RegionModule.getInstance().removeRegion(args[1]);
			player.sendMessage(RegionLang.REGION_REMOVE);

			//Config
			RegionManager.getInstance().getConfig().set("Region." + args[1], null);
			RegionManager.getInstance().saveConfig();
		}
	}
}