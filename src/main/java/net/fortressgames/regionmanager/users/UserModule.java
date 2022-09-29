package net.fortressgames.regionmanager.users;

import net.fortressgames.fortressapi.players.FortressPlayer;
import net.fortressgames.fortressapi.players.FortressPlayerModule;
import net.fortressgames.regionmanager.RegionLang;
import net.fortressgames.regionmanager.RegionManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserModule implements Listener {

	private static UserModule instance;
	private final HashMap<FortressPlayer, User> users = new HashMap<>();

	public static UserModule getInstance() {
		if(instance == null) {
			instance = new UserModule();
		}

		return instance;
	}

	public User getUser(FortressPlayer fortressPlayer) {
		return this.users.get(fortressPlayer);
	}

	public void addUser(FortressPlayer fortressPlayer) {
		this.users.put(fortressPlayer, new User(fortressPlayer));
	}

	public List<User> getAllUsers() {
		return new ArrayList<>(users.values());
	}

	@EventHandler
	public void playerJoin(PlayerJoinEvent e) {
		this.addUser(FortressPlayer.getPlayer(e.getPlayer()));
	}

	@EventHandler
	public void playerQuit(PlayerQuitEvent e) {
		FortressPlayer fortressPlayer = FortressPlayer.getPlayer(e.getPlayer());

		if(RegionManager.getInstance().isCombatLog()) {
			if(getUser(fortressPlayer).getCombatTask() != null) {
				//combat logged!
				fortressPlayer.setHealth(0);

				FortressPlayerModule.getInstance().getOnlinePlayers().forEach(target -> target.sendMessage(RegionLang.combatLogged(target.getDisplayName())));
			}
		}

		this.users.remove(fortressPlayer);
	}
}