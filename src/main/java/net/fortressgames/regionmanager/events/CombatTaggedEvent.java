package net.fortressgames.regionmanager.events;

import lombok.Getter;
import net.fortressgames.fortressapi.events.FortressPlayerEvent;
import net.fortressgames.fortressapi.players.FortressPlayer;
import net.fortressgames.regionmanager.utils.CombatTaggedCause;
import org.bukkit.event.HandlerList;

public class CombatTaggedEvent extends FortressPlayerEvent {

	private static final HandlerList HANDLERS = new HandlerList();
	@Getter private final CombatTaggedCause cause;
	@Getter private final FortressPlayer causedPlayer;

	public CombatTaggedEvent(FortressPlayer player, CombatTaggedCause cause, FortressPlayer causedPlayer) {
		super(player);
		this.cause = cause;
		this.causedPlayer = causedPlayer;
	}

	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}