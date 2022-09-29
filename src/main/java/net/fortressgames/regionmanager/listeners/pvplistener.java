package net.fortressgames.regionmanager.listeners;

import net.fortressgames.fortressapi.players.FortressPlayer;
import net.fortressgames.regionmanager.RegionLang;
import net.fortressgames.regionmanager.RegionManager;
import net.fortressgames.regionmanager.events.CombatTaggedEvent;
import net.fortressgames.regionmanager.regions.Region;
import net.fortressgames.regionmanager.tasks.CombatTask;
import net.fortressgames.regionmanager.users.User;
import net.fortressgames.regionmanager.users.UserModule;
import net.fortressgames.regionmanager.utils.CombatTaggedCause;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class pvplistener implements Listener {

	@EventHandler
	public void EntityDamageFortress(EntityDamageByEntityEvent e) {

		if(e.getDamager() instanceof Player player) {
			User playerUser = UserModule.getInstance().getUser(player);

			if(e.getEntity() instanceof Player target) {
				User targetUser = UserModule.getInstance().getUser(target);

				for(Region region : playerUser.getRegionsInOrder()) {
					if(region.getFlags().contains("PVP_TRUE")) {
						break;
					}

					if(region.getFlags().contains("PVP_FALSE")) {

						e.setCancelled(true);
						playerUser.getFortressPlayer().sendMessage(RegionLang.PVP_FALSE);
						return;
					}
				}

				Bukkit.getPluginManager().callEvent(new CombatTaggedEvent(playerUser.getFortressPlayer(), CombatTaggedCause.ATTACKED, targetUser.getFortressPlayer()));
				Bukkit.getPluginManager().callEvent(new CombatTaggedEvent(targetUser.getFortressPlayer(), CombatTaggedCause.HIT, playerUser.getFortressPlayer()));

				tag(playerUser, playerUser.getFortressPlayer());
				tag(targetUser, targetUser.getFortressPlayer());
			}
		}
	}

	private void tag(User user, FortressPlayer player) {
		if(user.getCombatTask() == null) {
			user.setCombatTask(new CombatTask(user, player));
			player.sendMessage(RegionLang.COMBAT_TAG_ON);
		} else {
			user.getCombatTask().setCount(RegionManager.getInstance().getCombatLogTimer());
		}
	}
}