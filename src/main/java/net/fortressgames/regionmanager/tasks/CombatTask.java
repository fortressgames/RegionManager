package net.fortressgames.regionmanager.tasks;

import lombok.Setter;
import net.fortressgames.fortressapi.FortressRunnable;
import net.fortressgames.regionmanager.RegionLang;
import net.fortressgames.regionmanager.RegionManager;
import net.fortressgames.regionmanager.users.User;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class CombatTask extends FortressRunnable {

	private final User user;
	private final Player player;
	@Setter private int count;

	public CombatTask(User user, Player player) {
		this.user = user;
		this.player = player;
		count = RegionManager.getInstance().getCombatLogTimer();

		runTaskTimer(RegionManager.getInstance(), TimeUnit.SECONDS, 1);
	}

	@Override
	public void run() {

		count--;

		if(count == 0) {
			cancel();
			user.setCombatTask(null);
			player.sendMessage(RegionLang.COMBAT_TAG_OFF);
		}
	}
}