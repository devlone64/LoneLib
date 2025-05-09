package dev.lone64.LoneLib.common.menu.interfaces;

import dev.lone64.LoneLib.common.menu.data.MenuHolder;
import dev.lone64.LoneLib.common.menu.interfaces.handler.ClickHandler;
import dev.lone64.LoneLib.common.menu.interfaces.handler.CloseHandler;
import dev.lone64.LoneLib.common.menu.interfaces.handler.MenuHandler;
import dev.lone64.LoneLib.common.menu.interfaces.handler.PageHandler;
import dev.lone64.LoneLib.common.menu.page.Paginate;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public interface Menu<T> extends MenuHolder<T> {
    Menu<T> setPlayer(Player player);
    Menu<T> setRows(int rows);
    Menu<T> setPage(int page);
    Menu<T> setTitle(String title);
    Menu<T> setType(InventoryType type);

    default Menu<T> setTexture(String namespacedID) {return this;}
    default Menu<T> setOffset(int titleOffset, int textureOffset) {return this;}

    Menu<T> updatePage(int page);
    Menu<T> setPaginate(Paginate<?> paginate);

    Menu<T> onPage(PageHandler pageHandler);
    Menu<T> onInit(MenuHandler initHandler);
    Menu<T> onMenu(MenuHandler menuHandler);
    Menu<T> onMenu(PageHandler menuPageHandler);

    Menu<T> onClickEvent(ClickHandler clickHandler);
    Menu<T> onCloseEvent(CloseHandler closeHandler);

    int getRows();
    int getPage();
    String getTitle();
    InventoryType getType();

    default int getTitleOffset() {return 0;}
    default int getTextureOffset() {return 0;}

    Paginate<?> getPaginate();
    PageHandler getPageHandler();
    MenuHandler getInitHandler();
    MenuHandler getMenuHandler();
    PageHandler getMenuPageHandler();

    ClickHandler getClickHandler();
    CloseHandler getCloseHandler();
}