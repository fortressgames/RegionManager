package net.fortressgames.regionmanager.users;

import net.fortressgames.fortressapi.players.FortressPlayer;
import net.fortressgames.fortressapi.players.FortressPlayerModule;
import net.fortressgames.regionmanager.RegionLang;
import net.fortressgames.regionmanager.RegionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserModule implements Listener {

	private static UserModule instance;
	private final HashMap<Player, User> users = new HashMap<>();

	public static UserModule getInstance() {
		if(instance == null) {
			instance = new UserModule();
		}

		return instance;
	}

	public User getUser(Player player) {
		return this.users.get(player);
	}

	public void addUser(Player player) {
		this.users.put(player, new User(FortressPlayer.getPlayer(player)));
	}

	public void clearUsers() {
		this.users.clear();
	}

	public List<User> getAllUsers() {
		return new ArrayList<>(users.values());
	}

	@EventHandler
	public void playerJoin(PlayerJoinEvent e) {
		this.addUser(e.getPlayer());
	}

	@EventHandler
	public void playerQuit(PlayerQuitEvent e) {
		FortressPlayer fortressPlayer = FortressPlayer.getPlayer(e.getPlayer());

		if(RegionManager.getInstance().isCombatLog()) {
			if(getUser(e.getPlayer()).getCombatTask() != null) {
				//combat logged!
				fortressPlayer.setHealth(0);

				FortressPlayerModule.getInstance().getOnlinePlayers().forEach(target -> target.sendMessage(RegionLang.combatLogged(target.getDisplayName())));
			}
		}

		this.users.remove(e.getPlayer());
	}
}