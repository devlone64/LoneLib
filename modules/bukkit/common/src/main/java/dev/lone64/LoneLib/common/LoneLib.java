package dev.lone64.LoneLib.common;

import dev.lone64.LoneLib.common.command.Command;
import dev.lone64.LoneLib.common.item.Item;
import dev.lone64.LoneLib.common.menu.Menu;
import dev.lone64.LoneLib.common.menu.page.Paginate;
import dev.lone64.LoneLib.common.menu.page.impl.ItemStackPage;
import dev.lone64.LoneLib.common.menu.page.impl.MaterialPage;
import dev.lone64.LoneLib.common.nbt.PersistentDataContainer;
import dev.lone64.LoneLib.common.nbt.entity.EntityPersistentDataContainer;
import dev.lone64.LoneLib.common.nbt.item.ItemPersistentDataContainer;
import dev.lone64.LoneLib.common.textarea.Textarea;
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

    public static Textarea createTextarea() {
        return Textarea.createTextarea();
    }

    public static dev.lone64.LoneLib.common.menu.interfaces.Menu<?> createMenu(Class<?> menuClass, Player player) {
        return Menu.createMenu(menuClass, player);
    }

    public static MaterialPage createMaterialPage() {
        return Paginate.createMaterialPage();
    }

    public static ItemStackPage createItemStackPage() {
        return Paginate.createItemStackPage();
    }

    public static <T> Paginate<T> createCustomPage(Class<? extends Paginate<T>> paginateClass) {
        return Paginate.createCustomPage(paginateClass);
    }

    public static Item createItem(Material material) {
        return Item.createItemStack(material);
    }

    public static Item createItem(ItemStack itemStack) {
        return Item.createItemStack(itemStack);
    }

    public static Command createCommand(Plugin plugin, String name) {
        return Command.createCommand(plugin, name);
    }

    public static PersistentDataContainer createEntityPersistentDataContainer(Entity entity) {
        return new EntityPersistentDataContainer(entity);
    }

    public static PersistentDataContainer createItemPersistentDataContainer(ItemStack itemStack) {
        return new ItemPersistentDataContainer(itemStack);
    }
}