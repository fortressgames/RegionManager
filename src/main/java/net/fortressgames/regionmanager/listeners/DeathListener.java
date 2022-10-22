package net.fortressgames.regionmanager.listeners;

import net.fortressgames.regionmanager.RegionManager;
import net.fortressgames.regionmanager.events.LeaveCombatTaggedEvent;
import net.fortressgames.regionmanager.users.User;
import net.fortressgames.regionmanager.users.UserModule;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

	@EventHandler
	public void death(PlayerDeathEvent e) {
		User user = UserModule.getInstance().getUser(e.getEntity());

		if(user.getCombatTask() != null) {
			user.getCombatTask().cancel();
			user.setCombatTask(null);

			Bukkit.getScheduler().runTask(RegionManager.getInstance(), () ->
					Bukkit.getPluginManager().callEvent(new LeaveCombatTaggedEvent(e.getEntity())));
		}
	}
}