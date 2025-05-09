package dev.lone64.LoneLib.common.menu.interfaces.handler;

import dev.lone64.LoneLib.common.menu.interfaces.Menu;
import org.bukkit.event.inventory.InventoryCloseEvent;

@FunctionalInterface
public interface CloseHandler {
    void onClose(Menu<?> menu, InventoryCloseEvent e);
}