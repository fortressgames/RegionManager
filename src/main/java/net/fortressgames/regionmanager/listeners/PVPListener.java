package net.fortressgames.regionmanager.listeners;

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

public class PVPListener implements Listener {

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
						player.sendMessage(RegionLang.PVP_FALSE);
						return;
					}
				}

				Bukkit.getPluginManager().callEvent(new CombatTaggedEvent(player, CombatTaggedCause.ATTACKED, target));
				Bukkit.getPluginManager().callEvent(new CombatTaggedEvent(target, CombatTaggedCause.HIT, player));

				tag(playerUser, player);
				tag(targetUser, target);
			}
		}
	}

	private void tag(User user, Player player) {
		if(user.getCombatTask() == null) {
			user.setCombatTask(new CombatTask(user, player));
			player.sendMessage(RegionLang.COMBAT_TAG_ON);
		} else {
			user.getCombatTask().setCount(RegionManager.getInstance().getCombatLogTimer());
		}
	}
}