package net.fortressgames.regionmanager.commands.subcommands;

import net.fortressgames.fortressapi.Lang;
import net.fortressgames.fortressapi.players.FortressPlayer;
import net.fortressgames.regionmanager.regions.Region;
import net.fortressgames.regionmanager.regions.RegionModule;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public abstract class RegionList {

	public static void execute(FortressPlayer player, Region region, String[] args) {

		player.sendMessage(Lang.LINE);
		player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "Regions: " + ChatColor.GRAY +
				"(" + ChatColor.RED + RegionModule.getInstance().getAll().size() + ChatColor.GRAY + ")");

		List<String> list = new ArrayList<>();
		RegionModule.getInstance().getAll().forEach(rg -> list.add(rg.getName()));

		if(list.isEmpty()) {
			player.sendMessage(ChatColor.RED + "List is empty!");

		} else {
			player.sendMessage(ChatColor.GREEN + list.toString()
					.replace("[", ChatColor.DARK_GRAY + "[" + ChatColor.RESET)
					.replace("]", ChatColor.DARK_GRAY + "]" + ChatColor.RESET)
					.replace(",", ChatColor.DARK_GRAY + "," + ChatColor.RESET)
			);
		}

		player.sendMessage(Lang.LINE);
	}
}