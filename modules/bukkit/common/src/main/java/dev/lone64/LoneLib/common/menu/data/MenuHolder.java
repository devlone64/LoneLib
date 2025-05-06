package dev.lone64.LoneLib.common.menu.data;

import org.bukkit.inventory.InventoryHolder;

public interface MenuHolder<T> extends InventoryHolder {
    void show();
    void update();
    T createInventory();
}