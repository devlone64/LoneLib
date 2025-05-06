package dev.lone64.LoneLib.common.menu.data;

import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class BukkitMenu {
    public static MenuHolder<?> get(Class<?> screenClass, Player sender) {
        try {
            return (MenuHolder<?>) screenClass.getConstructor(Player.class).newInstance(sender);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            return null;
        }
    }
}