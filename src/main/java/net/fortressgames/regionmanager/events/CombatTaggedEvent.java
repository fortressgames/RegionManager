package net.fortressgames.regionmanager.events;

import lombok.Getter;
import net.fortressgames.regionmanager.utils.CombatTaggedCause;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class CombatTaggedEvent extends PlayerEvent {

	private static final HandlerList HANDLERS = new HandlerList();
	@Getter private final CombatTaggedCause cause;
	@Getter private final Player causedPlayer;

	public CombatTaggedEvent(Player player, CombatTaggedCause cause, Player causedPlayer) {
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