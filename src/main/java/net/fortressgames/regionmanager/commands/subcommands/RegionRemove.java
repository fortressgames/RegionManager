package net.fortressgames.regionmanager.commands.subcommands;

import net.fortressgames.fortressapi.players.FortressPlayer;
import net.fortressgames.regionmanager.RegionManager;
import net.fortressgames.regionmanager.regions.Region;
import net.fortressgames.regionmanager.regions.RegionModule;
import org.bukkit.ChatColor;

public abstract class RegionRemove {

	public static void execute(FortressPlayer player, Region region, String[] args) {

		if(args.length == 2) {
			RegionModule.getInstance().removeRegion(args[1]);
			player.sendMessage(ChatColor.GREEN + "Region removed!");

			//Config
			RegionManager.getInstance().getConfig().set("Region." + args[1], null);
			RegionManager.getInstance().saveConfig();
		}
	}
}