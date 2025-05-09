package dev.lone64.LoneLib.common.textarea;

import dev.lone64.LoneLib.common.textarea.other.TextareaManager;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@Getter
public class TextareaInstance {
    private Plugin plugin;
    private Player player;

    private String title;
    private String subtitle;

    private int fadeIn = 20;
    private int fadeOut = 20;

    private boolean isDelay = false;
    private boolean isSlowness = true;
    private boolean isBlindness = true;
    private boolean isJumpToCancel = true;

    private UserHandler initHandler;
    private TextHandler inputHandler;
    private UserHandler cancelHandler;

    public TextareaInstance setPlugin(Plugin plugin) {
        this.plugin = plugin;
        return this;
    }

    public TextareaInstance setPlayer(Player player) {
        this.player = player;
        return this;
    }

    public TextareaInstance setTitle(String title) {
        this.title = title;
        return this;
    }

    public TextareaInstance setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public TextareaInstance setFadeIn(int fadeIn) {
        this.fadeIn = fadeIn;
        return this;
    }

    public TextareaInstance setFadeOut(int fadeOut) {
        this.fadeOut = fadeOut;
        return this;
    }

    public TextareaInstance setDelay(boolean isDelay) {
        this.isDelay = isDelay;
        return this;
    }

    public TextareaInstance setSlowness(boolean isSlowness) {
        this.isSlowness = isSlowness;
        return this;
    }

    public TextareaInstance setBlindness(boolean isBlindness) {
        this.isBlindness = isBlindness;
        return this;
    }

    public TextareaInstance setJumpToCancel(boolean isJumpToCancel) {
        this.isJumpToCancel = isJumpToCancel;
        return this;
    }

    public TextareaInstance onInit(UserHandler initHandler) {
        this.initHandler = initHandler;
        return this;
    }

    public TextareaInstance onInput(TextHandler inputHandler) {
        this.inputHandler = inputHandler;
        return this;
    }

    public TextareaInstance onCancel(UserHandler cancelHandler) {
        this.cancelHandler = cancelHandler;
        return this;
    }

    public void sent() {
        if (this.player == null) return;
        TextareaManager.setKey(this.player, this);
    }

    @FunctionalInterface
    public interface UserHandler {
        void onHandle(Player sender);
    }

    @FunctionalInterface
    public interface TextHandler {
        boolean onHandle(Player sender, String value);
    }

    public static TextareaInstance getInstance() {
        return new TextareaInstance();
    }
}