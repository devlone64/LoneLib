package dev.lone64.LoneLib.common.itemstack;

import dev.lone64.LoneLib.common.util.nms.NmsVersion;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Getter
public class BukkitNmsItemStack {
    private ItemStack item;

    public BukkitNmsItemStack setMaterial(Material material) {
        this.item = new ItemStack(material, 1);
        return this;
    }

    public BukkitNmsItemStack setItem(ItemStack item) {
        this.item = item;
        return this;
    }

    public BukkitNmsItemStack setNbt(String path, String value) {
        var itemMeta = this.getItemMeta();
        if (itemMeta == null) return this;
        if (NmsVersion.getCurrentVersion().isItemName()) {
            var localizedName = this.getItemMeta().getItemName();
            itemMeta.setItemName("%s[%s:%s]/".formatted(
                    localizedName, path, value
            ));
        }
        this.getItem().setItemMeta(itemMeta);
        return this;
    }

    public String getNbt(String path) {
        var itemMeta = this.getItemMeta();
        if (itemMeta == null) return null;
        for (var data : itemMeta.getItemName().split("/")) {
            if (data.contains(path)) {
                return data
                        .replace(path, "")
                        .replace("[", "")
                        .replace("]", "")
                        .replace("/", "");
            }
        }
        return null;
    }

    public ItemMeta getItemMeta() {
        return getItem().getItemMeta();
    }

    public static BukkitNmsItemStack makeItem(ItemStack src) {
        return new BukkitNmsItemStack().setItem(src);
    }
}