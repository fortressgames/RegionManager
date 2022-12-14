package net.fortressgames.regionmanager.commands.subcommands;

import net.fortressgames.regionmanager.RegionLang;
import net.fortressgames.regionmanager.regions.Region;
import org.bukkit.entity.Player;

public abstract class RegionPriority {

	public static void execute(Player player, Region region, String[] args) {

		if(args.length >= 3) {
			region.setPri(Integer.parseInt(args[2]));
			region.save();
			player.sendMessage(RegionLang.regionPri(args[2]));
		}
	}
}