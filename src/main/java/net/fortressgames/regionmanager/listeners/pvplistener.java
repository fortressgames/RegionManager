package net.fortressgames.regionmanager.listeners;

import net.fortressgames.regionmanager.RegionLang;
import net.fortressgames.regionmanager.RegionManager;
import net.fortressgames.regionmanager.regions.Region;
import net.fortressgames.regionmanager.tasks.CombatTask;
import net.fortressgames.regionmanager.users.User;
import net.fortressgames.regionmanager.users.UserModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class pvplistener implements Listener {

	@EventHandler
	public void EntityDamageFortress(EntityDamageByEntityEvent e) {

		if(e.getDamager() instanceof Player player) {
			if(e.getEntity() instanceof Player target) {

				User user = UserModule.getInstance().getUser(player);

				for(Region region : user.getRegionsInOrder()) {
					if(region.getFlags().contains("PVP_TRUE")) {
						break;
					}

					if(region.getFlags().contains("PVP_FALSE")) {

						e.setCancelled(true);
						player.sendMessage(RegionLang.PVP_FALSE);
						return;
					}
				}

				tag(user, player);
				tag(UserModule.getInstance().getUser(target), target);
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