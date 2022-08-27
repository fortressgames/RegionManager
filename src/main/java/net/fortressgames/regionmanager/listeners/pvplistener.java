package net.fortressgames.regionmanager.listeners;

import net.fortressgames.regionmanager.RegionLang;
import net.fortressgames.regionmanager.regions.Region;
import net.fortressgames.regionmanager.users.User;
import net.fortressgames.regionmanager.users.UserModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

//TODO test PVP
public class pvplistener implements Listener {

	@EventHandler
	public void EntityDamageFortress(EntityDamageEvent e) {

		if(e.getEntity() instanceof Player target) {

			User user = UserModule.getInstance().getUser(target);

			for(Region region : user.getRegionsInOrder()) {
				if(region.getFlags().contains("PVP_TRUE")) return;
			}

			e.setCancelled(true);
			target.sendMessage(RegionLang.PVP_FALSE);
		}
	}
}