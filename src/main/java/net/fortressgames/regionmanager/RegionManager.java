package net.fortressgames.regionmanager;

import lombok.Getter;
import net.fortressgames.fortressapi.commands.CommandModule;
import net.fortressgames.fortressapi.utils.ConsoleMessage;
import net.fortressgames.regionmanager.commands.RegionCommand;
import net.fortressgames.regionmanager.listeners.DeathListener;
import net.fortressgames.regionmanager.listeners.pvplistener;
import net.fortressgames.regionmanager.regions.RegionModule;
import net.fortressgames.regionmanager.tasks.MoveTask;
import net.fortressgames.regionmanager.users.UserModule;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class RegionManager extends JavaPlugin {

	@Getter private static RegionManager instance;
	@Getter private boolean combatLog;
	@Getter private int combatLogTimer;

	/**
	 * Called when plugin first loads by spigot and is called before onEnable
	 */
	@Override
	public void onLoad() {
		// Create Default folder
		if(!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}

		File regions = new File(getDataFolder() + "/Regions");
		if(!regions.exists()) {
			regions.mkdir();
		}
	}

	/**
	 * Called when the plugin is first loaded by Spigot
	 */
	@Override
	public void onEnable() {
		instance = this;

		loadConfig();

		// Register commands
		CommandModule.registerCommand(new RegionCommand());

		RegionModule.getInstance().loadRegions();

		// Register events
		this.getServer().getPluginManager().registerEvents(UserModule.getInstance(), this);
		this.getServer().getPluginManager().registerEvents(new pvplistener(), this);
		this.getServer().getPluginManager().registerEvents(new DeathListener(), this);

		new MoveTask().runTaskTimer(this, TimeUnit.MILLISECONDS, 15);

		getLogger().info(ConsoleMessage.GREEN + "Version: " + getDescription().getVersion() + " Enabled!" + ConsoleMessage.RESET);
	}

	/**
	 * Called when the server is restarted or stopped
	 */
	@Override
	public void onDisable() {
		getLogger().info(ConsoleMessage.RED + "Version: " + getDescription().getVersion() + " Disabled!" + ConsoleMessage.RESET);
	}

	public void loadConfig() {
		if(!getConfig().contains("COMBAT_LOGGING")) {
			getConfig().set("COMBAT_LOGGING", false);
			saveConfig();
		} else {
			combatLog = getConfig().getBoolean("COMBAT_LOGGING");
		}

		if(!getConfig().contains("COMBAT_LOGGING_DELAY")) {
			getConfig().set("COMBAT_LOGGING_DELAY", 30);
			saveConfig();
		} else {
			combatLogTimer = getConfig().getInt("COMBAT_LOGGING_DELAY");
		}
	}
}