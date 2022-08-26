package net.fortressgames.regionmanager.commands.subcommands;

import net.fortressgames.fortressapi.players.FortressPlayer;
import net.fortressgames.regionmanager.regions.Region;
import org.bukkit.ChatColor;

public abstract class RegionDisplayName {

	public static void execute(FortressPlayer player, Region region, String[] args) {

		if(args.length >= 3) {
			region.setDisplayName(args[2].replace("_", " "));
			region.save();

			player.sendMessage(ChatColor.GREEN + "Name updated!");
		}
	}
}