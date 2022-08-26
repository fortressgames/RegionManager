package net.fortressgames.regionmanager.commands.subcommands;

import net.fortressgames.fortressapi.players.FortressPlayer;
import net.fortressgames.fortressapi.utils.Vector3;
import net.fortressgames.regionmanager.regions.Region;
import net.fortressgames.regionmanager.users.User;
import net.fortressgames.regionmanager.users.UserModule;
import org.bukkit.ChatColor;

public abstract class RegionPos {

	public static void execute(FortressPlayer player, Region region, String[] args) {

		User user = UserModule.getInstance().getUser(player.getPlayer());

		if(args.length == 2) {
			user.getPoints().clear();
			player.sendMessage(ChatColor.GREEN + "Pos's cleared!");
			return;
		}

		user.getPoints().add(new Vector3(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ()));
		player.sendMessage(ChatColor.GREEN + "Pos added! " + ChatColor.RED + "#" + user.getPoints().size());
	}
}