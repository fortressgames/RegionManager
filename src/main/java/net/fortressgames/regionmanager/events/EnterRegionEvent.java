package net.fortressgames.regionmanager.events;

import lombok.Getter;
import net.fortressgames.fortressapi.events.FortressPlayerEvent;
import net.fortressgames.fortressapi.players.FortressPlayer;
import net.fortressgames.regionmanager.regions.Region;
import org.bukkit.event.HandlerList;

public class EnterRegionEvent extends FortressPlayerEvent {

	private static final HandlerList HANDLERS = new HandlerList();
	@Getter private final Region currentRegion;

	public EnterRegionEvent(FortressPlayer player, Region lastRegion) {
		super(player);
		this.currentRegion = lastRegion;
	}

	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}