package dev.lone64.LoneLib.common.menu;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import dev.lone.itemsadder.api.FontImages.TexturedInventoryWrapper;
import dev.lone64.LoneLib.common.menu.data.MenuHolder;
import dev.lone64.LoneLib.common.menu.page.Paginate;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import static dev.lone64.LoneLib.common.util.string.ColorUtil.format;

@Getter
public class TextureMenu implements MenuHolder<TexturedInventoryWrapper> {
    private Player player;
    private TexturedInventoryWrapper wrapper;

    private int page;
    private int rows;
    private String title;
    private int titleOffset;
    private int textureOffset;
    private InventoryType type;
    private FontImageWrapper texture;

    private Paginate<?> paginate;

    private PageHandler pageHandler;
    private FrameHandler initHandler;
    private FrameHandler frameHandler;
    private PageHandler framePageHandler;

    private ClickHandler clickHandler;
    private CloseHandler closeHandler;

    public TextureMenu(Player player) {
        this.player = player;

        this.page = 1;
        this.rows = 6;
        this.title = "";
        this.titleOffset = 0;
        this.textureOffset = -48;
        this.type = InventoryType.CHEST;
    }

    public TextureMenu setPlayer(Player player) {
        this.player = player;
        return this;
    }

    public TextureMenu setPage(int page) {
        this.page = page;
        return this;
    }

    public TextureMenu setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public TextureMenu setTitle(String title) {
        this.title = title;
        return this;
    }

    public TextureMenu setTitleOffset(int titleOffset) {
        this.titleOffset = titleOffset;
        return this;
    }

    public TextureMenu setTextureOffset(int textureOffset) {
        this.textureOffset = textureOffset;
        return this;
    }

    public TextureMenu setType(InventoryType type) {
        this.type = type;
        return this;
    }

    public TextureMenu setTexture(FontImageWrapper texture) {
        this.texture = texture;
        return this;
    }

    public TextureMenu updatePage(int page) {
        this.page += page;
        this.paginate.setPage(this.page);
        this.update();
        return this;
    }

    public TextureMenu setPaginate(Paginate<?> paginate) {
        this.paginate = paginate;
        return this;
    }

    public TextureMenu onPage(PageHandler pageHandler) {
        this.pageHandler = pageHandler;
        return this;
    }

    public TextureMenu onInit(FrameHandler initHandler) {
        this.initHandler = initHandler;
        return this;
    }

    public TextureMenu onFrame(FrameHandler frameHandler) {
        this.frameHandler = frameHandler;
        return this;
    }

    public TextureMenu onFrame(PageHandler framePageHandler) {
        this.framePageHandler = framePageHandler;
        return this;
    }

    public TextureMenu onClickEvent(ClickHandler clickHandler) {
        this.clickHandler = clickHandler;
        return this;
    }

    public TextureMenu onCloseEvent(CloseHandler closeHandler) {
        this.closeHandler = closeHandler;
        return this;
    }

    @Override
    public void show() {
        this.wrapper = createInventory();
        if (this.pageHandler != null && this.paginate != null && this.page > 1)
            this.pageHandler.onPage(this.paginate, this.player, this.wrapper.getInternal(), this.page);
        if (this.initHandler != null) this.initHandler.onFrame(this.player, this.wrapper.getInternal());
        if (this.frameHandler != null) this.frameHandler.onFrame(this.player, this.wrapper.getInternal());
        if (this.framePageHandler != null && this.paginate != null)
            this.framePageHandler.onPage(this.paginate, this.player, this.wrapper.getInternal(), this.page);
        this.wrapper.showInventory(this.player);
    }

    @Override
    public void update() {
        if (this.wrapper == null) this.wrapper = createInventory();
        if (this.pageHandler != null && this.paginate != null && this.page > 1)
            this.pageHandler.onPage(this.paginate, this.player, this.wrapper.getInternal(), this.page);
        if (this.initHandler != null) this.initHandler.onFrame(this.player, this.wrapper.getInternal());
        if (this.frameHandler != null) this.frameHandler.onFrame(this.player, this.wrapper.getInternal());
        if (this.framePageHandler != null && this.paginate != null)
            this.framePageHandler.onPage(this.paginate, this.player, this.wrapper.getInternal(), this.page);
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
        void onClick(TextureMenu screen, InventoryClickEvent e);
    }

    @FunctionalInterface
    public interface CloseHandler {
        void onClose(TextureMenu screen, InventoryCloseEvent e);
    }
}