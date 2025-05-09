package dev.lone64.LoneLib.common.menu.interfaces.handler;

import dev.lone64.LoneLib.common.menu.interfaces.Menu;
import org.bukkit.event.inventory.InventoryClickEvent;

@FunctionalInterface
public interface ClickHandler {
    void onClick(Menu<?> menu, InventoryClickEvent e);
}