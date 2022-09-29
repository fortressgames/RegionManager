package net.fortressgames.regionmanager.tasks;

import net.fortressgames.fortressapi.FortressRunnable;
import net.fortressgames.fortressapi.players.FortressPlayer;
import net.fortressgames.fortressapi.players.FortressPlayerModule;
import net.fortressgames.fortressapi.utils.Vector3;
import net.fortressgames.regionmanager.RegionLang;
import net.fortressgames.regionmanager.RegionManager;
import net.fortressgames.regionmanager.events.EnterRegionEvent;
import net.fortressgames.regionmanager.events.LeaveRegionEvent;
import net.fortressgames.regionmanager.regions.Region;
import net.fortressgames.regionmanager.regions.RegionModule;
import net.fortressgames.regionmanager.users.User;
import net.fortressgames.regionmanager.users.UserModule;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;

public class MoveTask extends FortressRunnable {

	@Override
	public void run() {
		for(FortressPlayer pp : FortressPlayerModule.getInstance().getAllUsers()) {

			// Add user if they are null (this happens after reload)
			if(UserModule.getInstance().getUser(pp.getPlayer()) == null) {
				UserModule.getInstance().addUser(pp.getPlayer());
			}

			check(pp);
		}
	}

	public static void check(FortressPlayer fortressPlayer) {
		User user = UserModule.getInstance().getUser(fortressPlayer.getPlayer());

		// List of all regions the player is inside of
		List<Region> currentInsideRegions = new ArrayList<>();

		for(Region region : RegionModule.getInstance().getAll()) {

			if(region.getName().equals("global")) continue;

			if(region.getRegionMaths().inside(new Vector3(fortressPlayer.getLocation().getBlockX(), fortressPlayer.getLocation().getBlockY(), fortressPlayer.getLocation().getBlockZ()))) {

				if(fortressPlayer.getWorld().equals(region.getWorld())) {
					currentInsideRegions.add(region);
				}
			}
		}

		// Loops inside regions and trigger new regions / flags
		for(Region inside : currentInsideRegions) {

			if(!user.getRegions().contains(inside)) {

				if(user.getCombatTask() != null) {
					if(inside.getFlags().contains("PVP_FALSE")) {
						fortressPlayer.getPlayer().teleport(user.getLastLocation());
						fortressPlayer.sendMessage(RegionLang.ENTRY_COMBAT);
						continue;
					}
				}

				if(inside.getFlags().contains("ENTRY_FALSE")) {

					if(!inside.getMembers().contains(fortressPlayer.getPlayer().getName())) {
						fortressPlayer.getPlayer().teleport(user.getLastLocation());
						fortressPlayer.sendMessage(RegionLang.ENTRY);
						continue;
					}
				}

				user.getRegions().add(inside);

				Bukkit.getScheduler().runTask(RegionManager.getInstance(), () -> Bukkit.getPluginManager().callEvent(new EnterRegionEvent(fortressPlayer, inside)));

				if(inside.getFlags().contains("TITLE_TRUE")) {
					fortressPlayer.sendTitle(ChatColor.translateAlternateColorCodes('&', inside.getDisplayName()), "", 10, 40, 10);
				}

				if(inside.getFlags().contains("ACTION_BAR_TRUE")) {
					fortressPlayer.sendActionBar(inside.getDisplayName());
				}

				for(String flag : inside.getFlags()) {
					if(flag.contains("ACTION") || flag.contains("TITLE")) continue;

					switch(flag.split("_")[0]) {

						// EXAMPLE SOUND _ BLOCK_NOTE_BLOCK_BELL
						case "SOUND" -> {
							try {
								fortressPlayer.playSound(Sound.valueOf(flag.replace("SOUND_", "").toUpperCase()));
							} catch (IllegalArgumentException ignored) {}
						}

						// EXAMPLE EFFECT _ JUMP _ 10
						case "EFFECT" -> {
							try {
								fortressPlayer.addPotionEffect(PotionEffectType.getByName(flag.split("_")[1].toUpperCase()),
										Integer.MAX_VALUE, Integer.parseInt(flag.split("_")[2]));
							} catch (IllegalArgumentException ignored) {}
						}
					}
				}
			}
		}

		user.setLastLocation(fortressPlayer.getLocation());

		if(user.getRegions().isEmpty()) return;

		// Loops and removes any old regions
		try {
			user.getRegions().removeIf(region -> {

				// stops global being removed
				if(region.equals(RegionModule.getInstance().getGlobal())) return false;

				if(!currentInsideRegions.contains(region)) {
					Bukkit.getScheduler().runTask(RegionManager.getInstance(), () -> Bukkit.getPluginManager().callEvent(new LeaveRegionEvent(fortressPlayer, region)));

					for(String flag : region.getFlags()) {
						if(flag.contains("ACTION") || flag.contains("TITLE")) continue;

						switch(flag.split("_")[0]) {

							// EXAMPLE SOUND _ BLOCK_NOTE_BLOCK_BELL
							case "SOUND" -> {
								try {
									fortressPlayer.stopSound(Sound.valueOf(flag.replace("SOUND_", "").toUpperCase()));
								} catch (IllegalArgumentException ignored) {}
							}

							// EXAMPLE EFFECT _ JUMP
							case "EFFECT" -> {
								try {
									fortressPlayer.removePotionEffect(PotionEffectType.getByName(flag.split("_")[1].toUpperCase()));
								} catch (IllegalArgumentException ignored) {}
							}
						}
					}
					return true;
				}

				return false;
			});
		} catch (ConcurrentModificationException ignored) {
		}
	}
}