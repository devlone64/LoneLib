package dev.lone64.LoneLib.common.menu.interfaces.handler;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@FunctionalInterface
public interface MenuHandler {
    void onMenu(Inventory inventory, Player sender);
}