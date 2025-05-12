package dev.lone64.LoneLib.common.menu;

import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("unchecked")
public class Menu {
    public static dev.lone64.LoneLib.common.menu.interfaces.Menu<?> createMenu(Class<?> menuClass, Player sender) {
        try {
            return ((Class<dev.lone64.LoneLib.common.menu.interfaces.Menu<?>>) menuClass).getConstructor(Player.class).newInstance(sender);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new IllegalArgumentException("An error occurred while fetching the menu.", e);
        }
    }
}