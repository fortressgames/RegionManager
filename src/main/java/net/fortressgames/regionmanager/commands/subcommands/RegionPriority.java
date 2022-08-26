package net.fortressgames.regionmanager.commands.subcommands;

import net.fortressgames.fortressapi.players.FortressPlayer;
import net.fortressgames.regionmanager.regions.Region;
import org.bukkit.ChatColor;

public abstract class RegionPriority {

	public static void execute(FortressPlayer player, Region region, String[] args) {

		if(args.length >= 3) {
			region.setPri(Integer.parseInt(args[2]));
			region.save();
			player.sendMessage(ChatColor.GREEN + "Pri set to " + args[2]);
		}
	}
}