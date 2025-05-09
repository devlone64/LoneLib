package dev.lone64.LoneLib.common.menu.interfaces.handler;

import dev.lone64.LoneLib.common.menu.interfaces.Menu;
import dev.lone64.LoneLib.common.menu.page.Paginate;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@FunctionalInterface
public interface PageHandler {
    void onPage(Menu<?> menu, Inventory inventory, Paginate<?> paginate, Player sender, int page);
}