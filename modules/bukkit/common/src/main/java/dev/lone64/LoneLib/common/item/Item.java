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
        var itemMeta = this.getItemMeta();
        if (itemMeta == null) return this;
        itemMeta.setDisplayName(format(name));
        this.getItemStack().setItemMeta(itemMeta);
        return this;
    }
    
    public Item setLore(List<String> lore) {
        var itemMeta = this.getItemMeta();
        if (itemMeta == null) return this;
        itemMeta.setLore(format(lore));
        this.getItemStack().setItemMeta(itemMeta);
        return this;
    }
    
    public Item setDamage(int damage) {
        var itemMeta = (Damageable) this.getItemMeta();
        if (itemMeta == null) return this;
        itemMeta.setDamage(damage);
        this.getItemStack().setItemMeta(itemMeta);
        return this;
    }
    
    public Item setCustomModelData(int customModelData) {
        var itemMeta = this.getItemMeta();
        if (itemMeta == null) return this;
        itemMeta.setCustomModelData(customModelData);
        this.getItemStack().setItemMeta(itemMeta);
        return this;
    }
    
    public Item setOwningPlayer(String owningPlayer) {
        var uuid = UUIDUtil.from(owningPlayer);
        if (uuid == null) return this;
        var itemMeta = (SkullMeta) this.getItemMeta();
        if (itemMeta == null) return this;
        if (NmsVersion.getCurrentVersion().isNewHead()) {
            var offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            itemMeta.setOwningPlayer(offlinePlayer);
        } else itemMeta.setOwner(owningPlayer);
        this.getItemStack().setItemMeta(itemMeta);
        return this;
    }
    
    public Item setOwningPlayer(OfflinePlayer owningPlayer) {
        return setOwningPlayer(owningPlayer.getName());
    }
    
    public Item setUnbreakable(boolean isUnbreakable) {
        var itemMeta = this.getItemMeta();
        if (itemMeta == null) return this;
        itemMeta.setUnbreakable(isUnbreakable);
        this.getItemStack().setItemMeta(itemMeta);
        return this;
    }
    
    public Item setLeatherColor(Color leatherColor) {
        var type = this.getItemStack().getType();
        if (!(type == Material.LEATHER_HELMET || type == Material.LEATHER_CHESTPLATE || type == Material.LEATHER_LEGGINGS || type == Material.LEATHER_BOOTS))
            throw new IllegalArgumentException("color(Color) only applicable for leather armor.");
        var itemMeta = (LeatherArmorMeta) this.getItemMeta();
        if (itemMeta == null) return this;
        itemMeta.setColor(leatherColor);
        this.getItemStack().setItemMeta(itemMeta);
        return this;
    }
    
    public Item addItemFlags(ItemFlag... itemFlags) {
        var itemMeta = this.getItemMeta();
        if (itemMeta == null) return this;
        itemMeta.addItemFlags(itemFlags);
        this.getItemStack().setItemMeta(itemMeta);
        return this;
    }
    
    public Item setEnchantments(EnchantData... enchantments) {
        var itemMeta = this.getItemMeta();
        if (itemMeta == null) return this;
        Arrays.stream(enchantments).forEach(enchant -> itemMeta.addEnchant(
                enchant.getEnchantment(), enchant.getStage(), enchant.isState()
        ));
        this.getItemStack().setItemMeta(itemMeta);
        return this;
    }
    
    public Item setAttributes(AttributeData... attributes) {
        var itemMeta = this.getItemMeta();
        if (itemMeta == null) return this;
        Arrays.stream(attributes).forEach(attribute -> itemMeta.addAttributeModifier(
                attribute.getAttribute(), attribute.getAttributeModifier()
        ));
        this.getItemStack().setItemMeta(itemMeta);
        return this;
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