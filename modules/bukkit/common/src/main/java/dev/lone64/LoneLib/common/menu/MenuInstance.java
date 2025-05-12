package dev.lone64.LoneLib.common.menu;

import dev.lone64.LoneLib.common.menu.interfaces.Menu;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("unchecked")
public class MenuInstance {
    public static Menu<?> loadMenu(Class<?> menuClass, Player sender) {
        try {
            return ((Class<Menu<?>>) menuClass).getConstructor(Player.class).newInstance(sender);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new IllegalArgumentException("An error occurred while fetching the menu.", e);
        }
    }
}