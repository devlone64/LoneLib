package dev.lone64.LoneLib.common.menu;

import dev.lone64.LoneLib.common.menu.interfaces.Menu;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class MenuInstance {
    public static <T> Menu<T> fetch(Class<Menu<T>> menuClass, Player sender) {
        try {
            return menuClass.getConstructor(Player.class).newInstance(sender);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new IllegalArgumentException("An error occurred while fetching the menu.", e);
        }
    }
}