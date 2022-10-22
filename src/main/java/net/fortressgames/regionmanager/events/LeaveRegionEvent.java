package net.fortressgames.regionmanager.events;

import lombok.Getter;
import net.fortressgames.regionmanager.regions.Region;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class LeaveRegionEvent extends PlayerEvent {

    private static final HandlerList HANDLERS = new HandlerList();
    @Getter private final Region lastRegion;

    public LeaveRegionEvent(Player player, Region lastRegion) {
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