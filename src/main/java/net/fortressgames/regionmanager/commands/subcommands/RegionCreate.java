package net.fortressgames.regionmanager.commands.subcommands;

import net.fortressgames.fortressapi.players.FortressPlayer;
import net.fortressgames.fortressapi.utils.Vector2;
import net.fortressgames.fortressapi.utils.Vector3;
import net.fortressgames.regionmanager.RegionLang;
import net.fortressgames.regionmanager.regions.Region;
import net.fortressgames.regionmanager.regions.RegionModule;
import net.fortressgames.regionmanager.users.UserModule;
import net.fortressgames.regionmanager.utils.RegionMaths;

import java.util.ArrayList;
import java.util.List;

public abstract class RegionCreate {

	public static void execute(FortressPlayer player, Region region, String[] args) {

		if(args.length == 2) {

			if(RegionModule.getInstance().isRegion(args[1])) {
				player.sendMessage(RegionLang.REGION_EXISTS);
				return;
			}

			if(UserModule.getInstance().getUser(player.getPlayer()).getPoints().size() == 0) {
				player.sendMessage(RegionLang.REGION_NOT_FOUND_RUN_POS);
				return;
			}

			List<Vector2> point = new ArrayList<>();
			int maxY = 0;
			int minY = 300;

			for(Vector3 p : UserModule.getInstance().getUser(player.getPlayer()).getPoints()) {
				point.add(p.toVector2());

				if(maxY < p.getBlockY()) {
					maxY = p.getBlockY();
				}

				if(minY > p.getBlockY()) {
					minY = p.getBlockY();
				}
			}

			RegionModule.getInstance().addRegion(args[1], new RegionMaths(point, maxY, minY), player.getWorld());

			UserModule.getInstance().getUser(player.getPlayer()).getPoints().clear();

			player.sendMessage(RegionLang.REGION_ADDED);
		}
	}
}