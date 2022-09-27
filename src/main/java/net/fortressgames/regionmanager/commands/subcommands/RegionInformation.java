package net.fortressgames.regionmanager.commands.subcommands;

import net.fortressgames.fortressapi.Lang;
import net.fortressgames.fortressapi.players.FortressPlayer;
import net.fortressgames.regionmanager.regions.Region;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public abstract class RegionInformation {

	public static void execute(FortressPlayer player, Region region, String[] args) {

		player.sendMessage(Lang.LINE);
		player.sendMessage(ChatColor.DARK_AQUA + ChatColor.BOLD.toString() + "Current region: " + ChatColor.WHITE + region.getName());
		player.sendMessage(ChatColor.AQUA + "Region Pri: " + ChatColor.WHITE + region.getPri());
		player.sendMessage(ChatColor.AQUA + "Region Points: " + ChatColor.RED + region.getRegionMaths().getPoints().size());

		List<String> flags = new ArrayList<>();
		for(String flag : region.getFlags()) {

			if(flag.contains("TRUE")) {
				flags.add(ChatColor.GREEN + flag.replace("_TRUE", "-TRUE"));
			}
			if(flag.contains("FALSE")) {
				flags.add(ChatColor.RED + flag.replace("_FALSE", "-FALSE"));
			}

			if(flag.contains("EFFECT")) {
				flags.add(ChatColor.GREEN + flag.split("_")[0] + "-" + flag.split("_")[1] + "-" + flag.split("_")[2]);
			}

			if(flag.contains("SOUND")) {
				flags.add(ChatColor.GREEN + flag.split("_")[0] + "-" + flag.replace(flag.split("_")[0] + "_", ""));
			}

			if(flag.contains("PARTICLE")) {
				flags.add(ChatColor.GREEN + flag.split("_")[0] + "-" + flag.split("_")[1] + "-" + flag.split("_")[2]);
			}
		}

		player.sendMessage(ChatColor.AQUA + "Region Flags: " + ChatColor.WHITE + flags.toString()
				.replace(",", ChatColor.YELLOW + ChatColor.BOLD.toString() + " |" + ChatColor.WHITE)
				.replace("[", "")
				.replace("]", "")
		);

		player.sendMessage(ChatColor.AQUA + "Display Name: " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', region.getDisplayName()));
		player.sendMessage(Lang.LINE);
	}
}