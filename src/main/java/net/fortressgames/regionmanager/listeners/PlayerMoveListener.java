package net.fortressgames.regionmanager.listeners;

import net.fortressgames.fortressapi.events.PlayerMoveTaskEvent;
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
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class PlayerMoveListener implements Listener {

	@EventHandler
	public void move(PlayerMoveTaskEvent e) {
		Player player = e.getPlayer();

		check(player);
	}

	public static void check(Player player) {
		if(UserModule.getInstance().getUser(player) == null) return;
		User user = UserModule.getInstance().getUser(player);

		// List of all regions the player is inside of
		List<Region> currentInsideRegions = new ArrayList<>();

		for (Region region : RegionModule.getInstance().getAll()) {

			if(region.getName().equals("global")) continue;

			if(region.getRegionMaths().inside(new Vector3(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ()))) {

				if(player.getWorld().equals(region.getWorld())) {
					currentInsideRegions.add(region);
				}
			}
		}

		// Loops inside regions and trigger new regions / flags
		for (Region inside : currentInsideRegions) {

			if(!user.getRegions().contains(inside)) {

				if(user.getCombatTask() != null) {
					if(inside.getFlags().contains("PVP_FALSE")) {
						player.teleport(user.getLastLocation());
						player.sendMessage(RegionLang.ENTRY_COMBAT);
						continue;
					}
				}

				if(inside.getFlags().contains("ENTRY_FALSE")) {

					if(!inside.getMembers().contains(player.getName())) {
						player.teleport(user.getLastLocation());
						player.sendMessage(RegionLang.ENTRY);
						continue;
					}
				}

				user.getRegions().add(inside);

				Bukkit.getScheduler().runTask(RegionManager.getInstance(), () -> Bukkit.getPluginManager().callEvent(new EnterRegionEvent(player, inside)));

				if(inside.getFlags().contains("TITLE_TRUE")) {
					player.sendTitle(ChatColor.translateAlternateColorCodes('&', inside.getDisplayName()), "", 10, 40, 10);
				}

				if(inside.getFlags().contains("ACTION_BAR_TRUE")) {
					user.sendActionBar(inside.getDisplayName());
				}

				for (String flag : inside.getFlags()) {
					if(flag.contains("ACTION") || flag.contains("TITLE")) continue;

					switch (flag.split("_")[0]) {

						// EXAMPLE SOUND _ BLOCK_NOTE_BLOCK_BELL
						case "SOUND" -> {
							try {
								player.playSound(player.getLocation(), Sound.valueOf(flag.replace("SOUND_", "").toUpperCase()), 100, 1);
							} catch (IllegalArgumentException ignored) {
							}
						}

						// EXAMPLE EFFECT _ JUMP _ 10
						case "EFFECT" -> {
							try {
								player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(flag.split("_")[1].toUpperCase()), Integer.MAX_VALUE,
										Integer.parseInt(flag.split("_")[2])));
							} catch (IllegalArgumentException ignored) {
							}
						}
					}
				}
			}
		}

		user.setLastLocation(player.getLocation());

		if(user.getRegions().isEmpty()) return;

		// Loops and removes any old regions
		try {
			user.getRegions().removeIf(region -> {

				// stops global being removed
				if(region.equals(RegionModule.getInstance().getGlobal())) return false;

				if(!currentInsideRegions.contains(region)) {
					Bukkit.getScheduler().runTask(RegionManager.getInstance(), () -> Bukkit.getPluginManager().callEvent(new LeaveRegionEvent(player, region)));

					for (String flag : region.getFlags()) {
						if(flag.contains("ACTION") || flag.contains("TITLE")) continue;

						switch (flag.split("_")[0]) {

							// EXAMPLE SOUND _ BLOCK_NOTE_BLOCK_BELL
							case "SOUND" -> {
								try {
									player.stopSound(Sound.valueOf(flag.replace("SOUND_", "").toUpperCase()));
								} catch (IllegalArgumentException ignored) {
								}
							}

							// EXAMPLE EFFECT _ JUMP
							case "EFFECT" -> {
								try {
									player.removePotionEffect(PotionEffectType.getByName(flag.split("_")[1].toUpperCase()));
								} catch (IllegalArgumentException ignored) {
								}
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