package net.fortressgames.regionmanager.listeners;

import net.fortressgames.fortressapi.players.FortressPlayer;
import net.fortressgames.regionmanager.users.User;
import net.fortressgames.regionmanager.users.UserModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

	@EventHandler
	public void death(PlayerDeathEvent e) {
		User user = UserModule.getInstance().getUser(FortressPlayer.getPlayer(e.getEntity()));

		if(user.getCombatTask() != null) {
			user.setCombatTask(null);
		}
	}
}