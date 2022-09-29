package net.fortressgames.regionmanager.tasks;

import lombok.Setter;
import net.fortressgames.fortressapi.FortressRunnable;
import net.fortressgames.fortressapi.players.FortressPlayer;
import net.fortressgames.regionmanager.RegionLang;
import net.fortressgames.regionmanager.RegionManager;
import net.fortressgames.regionmanager.events.LeaveCombatTaggedEvent;
import net.fortressgames.regionmanager.users.User;
import org.bukkit.Bukkit;

import java.util.concurrent.TimeUnit;

public class CombatTask extends FortressRunnable {

	private final User user;
	private final FortressPlayer fortressPlayer;
	@Setter private int count;

	public CombatTask(User user, FortressPlayer fortressPlayer) {
		this.user = user;
		this.fortressPlayer = fortressPlayer;
		count = RegionManager.getInstance().getCombatLogTimer();

		runTaskTimer(RegionManager.getInstance(), TimeUnit.SECONDS, 1);
	}

	@Override
	public void run() {

		count--;

		if(count == 0) {
			cancel();
			user.setCombatTask(null);
			fortressPlayer.sendMessage(RegionLang.COMBAT_TAG_OFF);

			Bukkit.getScheduler().runTask(RegionManager.getInstance(), () ->
					Bukkit.getPluginManager().callEvent(new LeaveCombatTaggedEvent(fortressPlayer)));
		}
	}
}