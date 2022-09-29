package net.fortressgames.regionmanager.events;

import lombok.Getter;
import net.fortressgames.fortressapi.events.FortressPlayerEvent;
import net.fortressgames.fortressapi.players.FortressPlayer;
import net.fortressgames.regionmanager.regions.Region;
import org.bukkit.event.HandlerList;

public class LeaveRegionEvent extends FortressPlayerEvent {

    private static final HandlerList HANDLERS = new HandlerList();
    @Getter private final Region lastRegion;

    public LeaveRegionEvent(FortressPlayer player, Region lastRegion) {
        super(player);
        this.lastRegion = lastRegion;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}