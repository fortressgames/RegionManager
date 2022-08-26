package net.fortressgames.regionmanager.commands.subcommands;

import net.fortressgames.fortressapi.Lang;
import net.fortressgames.fortressapi.players.FortressPlayer;
import net.fortressgames.regionmanager.regions.Region;
import org.bukkit.ChatColor;

public abstract class RegionInformation {

	public static void execute(FortressPlayer player, Region region, String[] args) {

		player.sendMessage(Lang.LINE);
		player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "Current region: " + ChatColor.WHITE + region.getName());
		player.sendMessage(ChatColor.GREEN + "Region Pri: " + ChatColor.WHITE + region.getPri());
		player.sendMessage(ChatColor.GREEN + "Region Points: " + ChatColor.RED + region.getRegionMaths().getPoints().size());
		player.sendMessage(ChatColor.GREEN + "Region Flags: " + ChatColor.WHITE + region.getFlags().toString());
		player.sendMessage(ChatColor.GREEN + "Display Name: " + ChatColor.WHITE + region.getDisplayName());
		player.sendMessage(Lang.LINE);
	}
}