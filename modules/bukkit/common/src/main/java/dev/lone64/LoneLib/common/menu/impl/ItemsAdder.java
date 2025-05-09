package dev.lone64.LoneLib.common.menu.impl;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import dev.lone.itemsadder.api.FontImages.TexturedInventoryWrapper;
import dev.lone64.LoneLib.common.menu.interfaces.Menu;
import dev.lone64.LoneLib.common.menu.interfaces.handler.ClickHandler;
import dev.lone64.LoneLib.common.menu.interfaces.handler.CloseHandler;
import dev.lone64.LoneLib.common.menu.interfaces.handler.MenuHandler;
import dev.lone64.LoneLib.common.menu.interfaces.handler.PageHandler;
import dev.lone64.LoneLib.common.menu.page.Paginate;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import static dev.lone64.LoneLib.common.util.string.ColorUtil.format;

public class ItemsAdder implements Menu<TexturedInventoryWrapper> {
    private Player player;
    private TexturedInventoryWrapper wrapper;

    private int rows;
    private int page;
    private String title;
    private int titleOffset;
    private int textureOffset;
    private InventoryType type;
    private FontImageWrapper texture;

    private Paginate<?> paginate;

    private PageHandler pageHandler;
    private MenuHandler initHandler;
    private MenuHandler menuHandler;
    private PageHandler menuPageHandler;

    private ClickHandler clickHandler;
    private CloseHandler closeHandler;

    public ItemsAdder(Player player) {
        this.player = player;

        this.rows = 6;
        this.page = 1;
        this.title = "";
        this.titleOffset = 0;
        this.textureOffset = -48;
        this.type = InventoryType.CHEST;
    }

    @Override
    public ItemsAdder setPlayer(Player player) {
        this.player = player;
        return this;
    }

    @Override
    public ItemsAdder setRows(int rows) {
        this.rows = rows;
        return this;
    }

    @Override
    public ItemsAdder setPage(int page) {
        this.page = page;
        return this;
    }

    @Override
    public ItemsAdder setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public ItemsAdder setType(InventoryType type) {
        this.type = type;
        return this;
    }

    @Override
    public ItemsAdder setTexture(String namespacedID) {
        this.texture = new FontImageWrapper(namespacedID);
        return this;
    }

    @Override
    public ItemsAdder setOffset(int titleOffset, int textureOffset) {
        this.titleOffset = titleOffset;
        this.textureOffset = textureOffset;
        return this;
    }

    @Override
    public ItemsAdder updatePage(int page) {
        this.page += page;
        this.paginate.setPage(this.page);
        this.update();
        return this;
    }

    @Override
    public ItemsAdder setPaginate(Paginate<?> paginate) {
        this.paginate = paginate;
        return this;
    }

    @Override
    public ItemsAdder onPage(PageHandler pageHandler) {
        return this;
    }

    @Override
    public ItemsAdder onInit(MenuHandler initHandler) {
        this.initHandler = initHandler;
        return this;
    }

    @Override
    public ItemsAdder onMenu(MenuHandler menuHandler) {
        this.menuHandler = menuHandler;
        return this;
    }

    @Override
    public ItemsAdder onMenu(PageHandler menuPageHandler) {
        this.menuPageHandler = menuPageHandler;
        return this;
    }

    @Override
    public ItemsAdder onClickEvent(ClickHandler clickHandler) {
        this.clickHandler = clickHandler;
        return this;
    }

    @Override
    public ItemsAdder onCloseEvent(CloseHandler closeHandler) {
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
    public int getTitleOffset() {
        return this.titleOffset;
    }

    @Override
    public int getTextureOffset() {
        return this.textureOffset;
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
        this.wrapper = createInventory();
        if (this.pageHandler != null && this.paginate != null && this.page > 1)
            this.pageHandler.onPage(this, this.wrapper.getInternal(), this.paginate, this.player, this.page);
        if (this.initHandler != null) this.initHandler.onMenu(this.wrapper.getInternal(), this.player);
        if (this.menuHandler != null) this.menuHandler.onMenu(this.wrapper.getInternal(), this.player);
        if (this.menuPageHandler != null && this.paginate != null)
            this.menuPageHandler.onPage(this, this.wrapper.getInternal(), this.paginate, this.player, this.page);
        this.wrapper.showInventory(this.player);
    }

    @Override
    public void update() {
        if (this.wrapper == null) this.wrapper = createInventory();
        if (this.pageHandler != null && this.paginate != null && this.page > 1)
            this.pageHandler.onPage(this, this.wrapper.getInternal(), this.paginate, this.player, this.page);
        if (this.initHandler != null) this.initHandler.onMenu(this.wrapper.getInternal(), this.player);
        if (this.menuHandler != null) this.menuHandler.onMenu(this.wrapper.getInternal(), this.player);
        if (this.menuPageHandler != null && this.paginate != null)
            this.menuPageHandler.onPage(this, this.wrapper.getInternal(), this.paginate, this.player, this.page);
    }

    @Override
    public TexturedInventoryWrapper createInventory() {
        return type == InventoryType.CHEST ?
                new TexturedInventoryWrapper(this, this.rows * 9, format(this.title), this.titleOffset, this.textureOffset, this.texture) :
                new TexturedInventoryWrapper(this, this.type, format(this.title), this.titleOffset, this.textureOffset, this.texture);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return wrapper.getInternal();
    }
}