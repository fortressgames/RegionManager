package net.fortressgames.regionmanager.commands.subcommands;

import net.fortressgames.fortressapi.utils.Vector3;
import net.fortressgames.regionmanager.RegionLang;
import net.fortressgames.regionmanager.regions.Region;
import net.fortressgames.regionmanager.users.User;
import net.fortressgames.regionmanager.users.UserModule;
import org.bukkit.entity.Player;

public abstract class RegionPos {

	public static void execute(Player player, Region region, String[] args) {

		User user = UserModule.getInstance().getUser(player);

		if(args.length == 2) {
			user.getPoints().clear();
			player.sendMessage(RegionLang.REGION_POS_CLEAR);
			return;
		}

		user.getPoints().add(new Vector3(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ()));
		player.sendMessage(RegionLang.regionPos(String.valueOf(user.getPoints().size())));
	}
}