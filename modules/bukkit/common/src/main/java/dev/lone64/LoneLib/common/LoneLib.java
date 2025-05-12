package dev.lone64.LoneLib.common;

import dev.lone64.LoneLib.common.command.CommandInstance;
import dev.lone64.LoneLib.common.itemstack.ItemStackInstance;
import dev.lone64.LoneLib.common.menu.MenuInstance;
import dev.lone64.LoneLib.common.menu.interfaces.Menu;
import dev.lone64.LoneLib.common.nbt.PersistentDataContainer;
import dev.lone64.LoneLib.common.nbt.entity.EntityPersistentDataContainer;
import dev.lone64.LoneLib.common.nbt.item.ItemPersistentDataContainer;
import dev.lone64.LoneLib.common.textarea.TextareaInstance;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class LoneLib {
    public static Economy getEconomy() {
        var provider = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (provider == null) return null;
        return provider.getProvider();
    }

    public static TextareaInstance createTextarea() {
        return TextareaInstance.loadTextarea();
    }

    public static Menu<?> createMenu(Class<?> menuClass, Player player) {
        return MenuInstance.loadMenu(menuClass, player);
    }

    public static ItemStackInstance createItem(Material material) {
        return ItemStackInstance.loadItem(material);
    }

    public static ItemStackInstance createItem(ItemStack itemStack) {
        return ItemStackInstance.loadItem(itemStack);
    }

    public static CommandInstance createCommand(Plugin plugin, String name) {
        return CommandInstance.loadCommand(plugin, name);
    }

    public static PersistentDataContainer createEntityPersistentDataContainer(Entity entity) {
        return new EntityPersistentDataContainer(entity);
    }

    public static PersistentDataContainer createItemPersistentDataContainer(ItemStack itemStack) {
        return new ItemPersistentDataContainer(itemStack);
    }
}