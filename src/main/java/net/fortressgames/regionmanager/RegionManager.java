package net.fortressgames.regionmanager;

import lombok.Getter;
import net.fortressgames.fortressapi.commands.CommandModule;
import net.fortressgames.fortressapi.utils.ConsoleMessage;
import net.fortressgames.regionmanager.commands.RegionCommand;
import net.fortressgames.regionmanager.listeners.pvplistener;
import net.fortressgames.regionmanager.regions.RegionModule;
import net.fortressgames.regionmanager.tasks.MoveTask;
import net.fortressgames.regionmanager.users.UserModule;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public class RegionManager extends JavaPlugin {

	@Getter private static RegionManager instance;
	@Getter private boolean combatLog;
	@Getter private final int combatLogTimer = 30;

	/**
	 * Called when plugin first loads by spigot and is called before onEnable
	 */
	@Override
	public void onLoad() {
		// Create Default folder
		if(!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
	}

	/**
	 * Called when the plugin is first loaded by Spigot
	 */
	@Override
	public void onEnable() {
		instance = this;

		if(!getConfig().contains("COMBAT_LOGGING")) {
			getConfig().set("COMBAT_LOGGING", false);
			saveConfig();
		} else {
			combatLog = getConfig().getBoolean("COMBAT_LOGGING");
		}

		// Register commands
		CommandModule.registerCommand(new RegionCommand());

		RegionModule.getInstance().loadRegions();

		// Register events
		this.getServer().getPluginManager().registerEvents(UserModule.getInstance(), this);
		this.getServer().getPluginManager().registerEvents(new pvplistener(), this);

		new MoveTask().runTaskTimer(this, TimeUnit.SECONDS, 1);

		getLogger().info(ConsoleMessage.GREEN + "Version: " + getDescription().getVersion() + " Enabled!" + ConsoleMessage.RESET);
	}

	/**
	 * Called when the server is restarted or stopped
	 */
	@Override
	public void onDisable() {
		getLogger().info(ConsoleMessage.RED + "Version: " + getDescription().getVersion() + " Disabled!" + ConsoleMessage.RESET);
	}
}