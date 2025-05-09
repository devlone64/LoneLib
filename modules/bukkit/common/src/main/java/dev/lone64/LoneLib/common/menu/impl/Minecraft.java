package dev.lone64.LoneLib.common.menu.impl;

import dev.lone64.LoneLib.common.menu.interfaces.Menu;
import dev.lone64.LoneLib.common.menu.interfaces.handler.ClickHandler;
import dev.lone64.LoneLib.common.menu.interfaces.handler.CloseHandler;
import dev.lone64.LoneLib.common.menu.interfaces.handler.MenuHandler;
import dev.lone64.LoneLib.common.menu.interfaces.handler.PageHandler;
import dev.lone64.LoneLib.common.menu.page.Paginate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import static dev.lone64.LoneLib.common.util.string.ColorUtil.format;

public class Minecraft implements Menu<Inventory> {
    private Player player;
    private Inventory inventory;

    private int page;
    private int rows;
    private String title;
    private InventoryType type;

    private Paginate<?> paginate;

    private PageHandler pageHandler;
    private MenuHandler initHandler;
    private MenuHandler menuHandler;
    private PageHandler menuPageHandler;

    private ClickHandler clickHandler;
    private CloseHandler closeHandler;

    public Minecraft(Player player) {
        this.player = player;

        this.rows = 6;
        this.page = 1;
        this.title = "";
        this.type = InventoryType.CHEST;
    }

    @Override
    public Minecraft setPlayer(Player player) {
        this.player = player;
        return this;
    }

    @Override
    public Minecraft setRows(int rows) {
        this.rows = rows;
        return this;
    }

    @Override
    public Minecraft setPage(int page) {
        this.page = page;
        return this;
    }

    @Override
    public Minecraft setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public Minecraft setType(InventoryType type) {
        this.type = type;
        return this;
    }

    @Override
    public Minecraft updatePage(int page) {
        this.page += page;
        this.paginate.setPage(this.page);
        this.update();
        return this;
    }

    @Override
    public Minecraft setPaginate(Paginate<?> paginate) {
        this.paginate = paginate;
        return this;
    }

    @Override
    public Minecraft onPage(PageHandler pageHandler) {
        this.pageHandler = pageHandler;
        return this;
    }

    @Override
    public Minecraft onInit(MenuHandler initHandler) {
        this.initHandler = initHandler;
        return this;
    }

    @Override
    public Minecraft onMenu(MenuHandler menuHandler) {
        this.menuHandler = menuHandler;
        return this;
    }

    @Override
    public Minecraft onMenu(PageHandler menuPageHandler) {
        this.menuPageHandler = menuPageHandler;
        return this;
    }

    @Override
    public Minecraft onClickEvent(ClickHandler clickHandler) {
        this.clickHandler = clickHandler;
        return this;
    }

    @Override
    public Minecraft onCloseEvent(CloseHandler closeHandler) {
        this.closeHandler = closeHandler;
        return this;
    }

    @Override
    public int getRows() {
        return this.rows;
    }

    @Override
    public int getPage() {
        return this.page;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public InventoryType getType() {
        return this.type;
    }

    @Override
    public Paginate<?> getPaginate() {
        return this.paginate;
    }

    @Override
    public PageHandler getPageHandler() {
        return this.pageHandler;
    }

    @Override
    public MenuHandler getInitHandler() {
        return this.initHandler;
    }

    @Override
    public MenuHandler getMenuHandler() {
        return this.menuHandler;
    }

    @Override
    public PageHandler getMenuPageHandler() {
        return this.menuPageHandler;
    }

    @Override
    public ClickHandler getClickHandler() {
        return this.clickHandler;
    }

    @Override
    public CloseHandler getCloseHandler() {
        return this.closeHandler;
    }

    @Override
    public void show() {
        this.inventory = createInventory();
        if (this.pageHandler != null && this.paginate != null && this.page > 1)
            this.pageHandler.onPage(this, this.inventory, this.paginate, this.player, this.page);
        if (this.initHandler != null) this.initHandler.onMenu(this.inventory, this.player);
        if (this.menuHandler != null) this.menuHandler.onMenu(this.inventory, this.player);
        if (this.menuPageHandler != null && this.paginate != null)
            this.menuPageHandler.onPage(this, this.inventory, this.paginate, this.player, this.page);
        this.player.openInventory(this.inventory);
    }

    @Override
    public void update() {
        if (this.inventory == null) this.inventory = createInventory();
        if (this.pageHandler != null && this.paginate != null && this.page > 1)
            this.pageHandler.onPage(this, this.inventory, this.paginate, this.player, this.page);
        if (this.initHandler != null) this.initHandler.onMenu(this.inventory, this.player);
        if (this.menuHandler != null) this.menuHandler.onMenu(this.inventory, this.player);
        if (this.menuPageHandler != null && this.paginate != null)
            this.menuPageHandler.onPage(this, this.inventory, this.paginate, this.player, this.page);
    }

    @Override
    public Inventory createInventory() {
        return type == InventoryType.CHEST ?
                Bukkit.createInventory(this, this.rows * 9, format(this.title)) :
                Bukkit.createInventory(this, this.type, format(this.title));
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}