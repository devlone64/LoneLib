package dev.lone64.LoneLib.common.event;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class PlayerJumpEvent extends PlayerEvent implements Cancellable {

    @Getter private static final HandlerList handlerList = new HandlerList();

    private final PlayerStatisticIncrementEvent event;

    private boolean isCancelled = false;

    public PlayerJumpEvent(Player player, PlayerStatisticIncrementEvent event) {
        super(player);

        this.event = event;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
        if (isCancelled) {
            this.event.setCancelled(true);
            player.setVelocity(new Vector());
        }
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

}
