package net.fortressgames.regionmanager.events;

import net.fortressgames.fortressapi.events.FortressPlayerEvent;
import net.fortressgames.fortressapi.players.FortressPlayer;
import org.bukkit.event.HandlerList;

public class LeaveCombatTaggedEvent extends FortressPlayerEvent {

	private static final HandlerList HANDLERS = new HandlerList();

	public LeaveCombatTaggedEvent(FortressPlayer player) {
		super(player);
	}

	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}