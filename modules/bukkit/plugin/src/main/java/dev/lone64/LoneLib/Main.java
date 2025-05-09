package dev.lone64.LoneLib;

import dev.lone64.LoneLib.common.command.other.CommandManager;
import dev.lone64.LoneLib.common.event.PlayerJumpEvent;
import dev.lone64.LoneLib.common.menu.impl.Minecraft;
import dev.lone64.LoneLib.common.menu.impl.ItemsAdder;
import dev.lone64.LoneLib.common.textarea.other.TextareaManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.plugin.java.JavaPlugin;

import static dev.lone64.LoneLib.common.util.string.ColorUtil.format;

public class Main extends JavaPlugin {
    @Getter private static Main instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    private static class PlayerListener implements Listener {
        @EventHandler(priority = EventPriority.HIGH)
        public void onPlayerStatisticIncrement(PlayerStatisticIncrementEvent event) {
            if (event.getStatistic() == Statistic.JUMP) {
                Bukkit.getPluginManager().callEvent(new PlayerJumpEvent(event.getPlayer(), event));
            }
        }

        @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
        public void onPlayerCommandPreprocess(final PlayerCommandPreprocessEvent e) {
            final var player = e.getPlayer();
            final var commands = e.getMessage().split(" ");
            if (commands.length > 0 && commands[0] != null) {
                var command = CommandManager.find(commands[0].replace("/", ""));
                if (command != null && CommandManager.is(commands[0].replace("/", ""))) {
                    boolean isNullOrEmpty = command.getPermission() == null || command.getPermission().isEmpty();
                    if (!isNullOrEmpty && !player.hasPermission(command.getPermission())) {
                        e.setCancelled(true);

                        isNullOrEmpty = command.getPermissionMessage() == null || command.getPermissionMessage().isEmpty();
                        if (!isNullOrEmpty) player.sendMessage(format(command.getPermissionMessage()));
                        else player.sendMessage(format("&c당신은 해당 명령어를 사용할 권한이 없습니다."));
                    }
                }
            }
        }

        @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
        public void onInventoryClick(final InventoryClickEvent e) {
            if (e.getWhoClicked() instanceof Player) {
                if (e.getInventory().getHolder() instanceof Minecraft src) {
                    if (src.getClickHandler() != null) src.getClickHandler().onClick(src, e);
                } else if (e.getInventory().getHolder() instanceof ItemsAdder src) {
                    if (src.getClickHandler() != null) src.getClickHandler().onClick(src, e);
                }
            }
        }

        @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
        public void onInventoryClose(final InventoryCloseEvent e) {
            if (e.getPlayer() instanceof Player) {
                if (e.getInventory().getHolder() instanceof Minecraft src) {
                    if (src.getCloseHandler() != null) src.getCloseHandler().onClose(src, e);
                } else if (e.getInventory().getHolder() instanceof ItemsAdder src) {
                    if (src.getCloseHandler() != null) src.getCloseHandler().onClose(src, e);
                }
            }
        }

        @EventHandler(priority = EventPriority.HIGH)
        public void onPlayerQuit(final PlayerQuitEvent event) {
            if (TextareaManager.isKey(event.getPlayer())) {
                TextareaManager.removeKey(event.getPlayer());
            }
        }

        @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
        public void onPlayerJump(final PlayerJumpEvent event) {
            var object = TextareaManager.findKey(event.getPlayer());
            if (TextareaManager.isKey(event.getPlayer())) {
                TextareaManager.removeKey(event.getPlayer());
                if (object.getCancelHandler() != null) {
                    object.getCancelHandler().onHandle(event.getPlayer());
                }
            }
        }

        @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
        public void onAsyncPlayerChat(final AsyncPlayerChatEvent event) {
            var object = TextareaManager.findKey(event.getPlayer());
            if (TextareaManager.isKey(event.getPlayer())) {
                event.setCancelled(true);
                if (object.getInputHandler() != null) {
                    Bukkit.getScheduler().runTask(object.getPlugin(), () -> {
                        if (object.getInputHandler().onHandle(event.getPlayer(), event.getMessage())) {
                            TextareaManager.removeKey(event.getPlayer());
                        }
                    });
                }
            }
        }
    }
}