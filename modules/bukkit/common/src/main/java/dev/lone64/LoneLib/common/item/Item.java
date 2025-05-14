package dev.lone64.LoneLib.common.item;

import dev.lone64.LoneLib.common.item.data.AttributeData;
import dev.lone64.LoneLib.common.item.data.EnchantData;
import dev.lone64.LoneLib.common.util.java.uuid.UUIDUtil;
import dev.lone64.LoneLib.common.util.nms.NmsVersion;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static dev.lone64.LoneLib.common.util.string.ColorUtil.format;

@Getter
public class Item {
    private ItemStack itemStack;

    public Item setMaterial(Material material) {
        this.itemStack = new ItemStack(material, 1);
        return this;
    }
    
    public Item setItem(ItemStack item) {
        this.itemStack = item;
        return this;
    }
    
    public Item setAmount(int amount) {
        this.getItemStack().setAmount(amount);
        return this;
    }
    
    public Item setName(String name) {
        var meta = this.getItemMeta();
        if (meta == null) return this;
        meta.setDisplayName(format(name));
        this.getItemStack().setItemMeta(meta);
        return this;
    }

    public Item setLore(List<String> lore) {
        var meta = this.getItemMeta();
        if (meta == null) return this;
        meta.setLore(format(lore));
        this.getItemStack().setItemMeta(meta);
        return this;
    }

    public Item setDamage(int damage) {
        var meta = (Damageable) this.getItemMeta();
        if (meta == null) return this;
        meta.setDamage(damage);
        this.getItemStack().setItemMeta(meta);
        return this;
    }

    public Item setCustomModelData(int customModelData) {
        var meta = this.getItemMeta();
        if (meta == null) return this;
        meta.setCustomModelData(customModelData);
        this.getItemStack().setItemMeta(meta);
        return this;
    }

    public Item setOwningPlayer(String owningPlayer) {
        var uuid = UUIDUtil.from(owningPlayer);
        if (uuid == null) return this;
        var meta = (SkullMeta) this.getItemMeta();
        if (meta == null) return this;
        if (NmsVersion.getCurrentVersion().isNewHead()) {
            var offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            meta.setOwningPlayer(offlinePlayer);
        } else meta.setOwner(owningPlayer);
        this.getItemStack().setItemMeta(meta);
        return this;
    }

    public Item setOwningPlayer(OfflinePlayer owningPlayer) {
        return setOwningPlayer(owningPlayer.getName());
    }

    public Item setUnbreakable(boolean isUnbreakable) {
        var meta = this.getItemMeta();
        if (meta == null) return this;
        meta.setUnbreakable(isUnbreakable);
        this.getItemStack().setItemMeta(meta);
        return this;
    }

    public Item setHideTooltip(boolean hideTooltip) {
        var meta = this.getItemMeta();
        if (meta == null) return this;
        meta.setHideTooltip(hideTooltip);
        this.getItemStack().setItemMeta(meta);
        return this;
    }

    public Item setLeatherColor(Color leatherColor) {
        var type = this.getItemStack().getType();
        if (!(type == Material.LEATHER_HELMET || type == Material.LEATHER_CHESTPLATE || type == Material.LEATHER_LEGGINGS || type == Material.LEATHER_BOOTS))
            throw new IllegalArgumentException("color(Color) only applicable for leather armor.");
        var meta = (LeatherArmorMeta) this.getItemMeta();
        if (meta == null) return this;
        meta.setColor(leatherColor);
        this.getItemStack().setItemMeta(meta);
        return this;
    }

    public Item addItemFlags(ItemFlag... itemFlags) {
        var meta = this.getItemMeta();
        if (meta == null) return this;
        meta.addItemFlags(itemFlags);
        this.getItemStack().setItemMeta(meta);
        return this;
    }

    public Item setEnchantments(EnchantData... enchantments) {
        var meta = this.getItemMeta();
        if (meta == null) return this;
        Arrays.stream(enchantments).forEach(enchant -> meta.addEnchant(
                enchant.getEnchantment(), enchant.getStage(), enchant.isState()
        ));
        this.getItemStack().setItemMeta(meta);
        return this;
    }

    public Item setAttributes(AttributeData... attributes) {
        var meta = this.getItemMeta();
        if (meta == null) return this;
        Arrays.stream(attributes).forEach(attribute -> meta.addAttributeModifier(
                attribute.getAttribute(), attribute.getAttributeModifier()
        ));
        this.getItemStack().setItemMeta(meta);
        return this;
    }

    public ItemStack apply(Consumer<ItemMeta> consumer) {
        var meta = this.getItemMeta();
        if (meta == null) return this.getItemStack();
        consumer.accept(meta);
        this.getItemStack().setItemMeta(meta);
        return this.getItemStack();
    }

    public ItemMeta getItemMeta() {
        return getItemStack().getItemMeta();
    }

    public static Item createItemStack(Material src) {
        return new Item().setMaterial(src);
    }
    public static Item createItemStack(ItemStack src) {
        return new Item().setItem(src);
    }
}