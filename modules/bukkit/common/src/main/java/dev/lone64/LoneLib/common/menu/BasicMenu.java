package dev.lone64.LoneLib.common.menu;

import dev.lone64.LoneLib.common.menu.data.MenuHolder;
import dev.lone64.LoneLib.common.menu.page.Paginate;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import static dev.lone64.LoneLib.common.util.string.ColorUtil.format;

@Getter
public class BasicMenu implements MenuHolder<Inventory> {
    private Player player;
    private Inventory inventory;

    private int page;
    private int rows;
    private String title;
    private InventoryType type;

    private Paginate<?> paginate;

    private PageHandler pageHandler;
    private FrameHandler initHandler;
    private FrameHandler frameHandler;
    private PageHandler framePageHandler;

    private ClickHandler clickHandler;
    private CloseHandler closeHandler;

    public BasicMenu(Player player) {
        this.player = player;

        this.page = 1;
        this.rows = 6;
        this.title = "";
        this.type = InventoryType.CHEST;
    }

    public BasicMenu setPlayer(Player player) {
        this.player = player;
        return this;
    }

    public BasicMenu setPage(int page) {
        this.page = page;
        return this;
    }

    public BasicMenu setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public BasicMenu setTitle(String title) {
        this.title = title;
        return this;
    }

    public BasicMenu setType(InventoryType type) {
        this.type = type;
        return this;
    }

    public BasicMenu updatePage(int page) {
        this.page += page;
        this.paginate.setPage(this.page);
        this.update();
        return this;
    }

    public BasicMenu setPaginate(Paginate<?> paginate) {
        this.paginate = paginate;
        return this;
    }

    public BasicMenu onPage(PageHandler pageHandler) {
        this.pageHandler = pageHandler;
        return this;
    }

    public BasicMenu onInit(FrameHandler initHandler) {
        this.initHandler = initHandler;
        return this;
    }

    public BasicMenu onFrame(FrameHandler frameHandler) {
        this.frameHandler = frameHandler;
        return this;
    }

    public BasicMenu onFrame(PageHandler framePageHandler) {
        this.framePageHandler = framePageHandler;
        return this;
    }

    public BasicMenu onClickEvent(ClickHandler clickHandler) {
        this.clickHandler = clickHandler;
        return this;
    }

    public BasicMenu onCloseEvent(CloseHandler closeHandler) {
        this.closeHandler = closeHandler;
        return this;
    }

    @Override
    public void show() {
        this.inventory = createInventory();
        if (this.pageHandler != null && this.paginate != null && this.page > 1)
            this.pageHandler.onPage(this.paginate, this.player, this.inventory, this.page);
        if (this.initHandler != null) this.initHandler.onFrame(this.player, this.inventory);
        if (this.frameHandler != null) this.frameHandler.onFrame(this.player, this.inventory);
        if (this.framePageHandler != null && this.paginate != null)
            this.framePageHandler.onPage(this.paginate, this.player, this.inventory, this.page);
        this.player.openInventory(this.inventory);
    }

    @Override
    public void update() {
        if (this.inventory == null) this.inventory = createInventory();
        if (this.pageHandler != null && this.paginate != null && this.page > 1)
            this.pageHandler.onPage(this.paginate, this.player, this.inventory, this.page);
        if (this.initHandler != null) this.initHandler.onFrame(this.player, this.inventory);
        if (this.frameHandler != null) this.frameHandler.onFrame(this.player, this.inventory);
        if (this.framePageHandler != null && this.paginate != null)
            this.framePageHandler.onPage(this.paginate, this.player, this.inventory, this.page);
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

    @FunctionalInterface
    public interface PageHandler {
        void onPage(Paginate<?> paginate, Player sender, Inventory inventory, int page);
    }

    @FunctionalInterface
    public interface FrameHandler {
        void onFrame(Player sender, Inventory inventory);
    }

    @FunctionalInterface
    public interface ClickHandler {
        void onClick(BasicMenu screen, InventoryClickEvent e);
    }

    @FunctionalInterface
    public interface CloseHandler {
        void onClose(BasicMenu screen, InventoryCloseEvent e);
    }

}