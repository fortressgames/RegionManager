package net.fortressgames.regionmanager.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class LeaveCombatTaggedEvent extends PlayerEvent {

	private static final HandlerList HANDLERS = new HandlerList();

	public LeaveCombatTaggedEvent(Player player) {
		super(player);
	}

	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}