package dev.lone64.LoneLib.common.itemstack;

import dev.lone64.LoneLib.common.itemstack.data.AttributeData;
import dev.lone64.LoneLib.common.itemstack.data.EnchantData;
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
public class BukkitItemStack {
    private ItemStack item;

    public BukkitItemStack setMaterial(Material material) {
        this.item = new ItemStack(material, 1);
        return this;
    }
    
    public BukkitItemStack setItem(ItemStack item) {
        this.item = item;
        return this;
    }
    
    public BukkitItemStack setAmount(int amount) {
        this.getItem().setAmount(amount);
        return this;
    }
    
    public BukkitItemStack setName(String name) {
        var itemMeta = this.getItemMeta();
        if (itemMeta == null) return this;
        itemMeta.setDisplayName(format(name));
        this.getItem().setItemMeta(itemMeta);
        return this;
    }
    
    public BukkitItemStack setLore(List<String> lore) {
        var itemMeta = this.getItemMeta();
        if (itemMeta == null) return this;
        itemMeta.setLore(format(lore));
        this.getItem().setItemMeta(itemMeta);
        return this;
    }
    
    public BukkitItemStack setDamage(int damage) {
        var itemMeta = (Damageable) this.getItemMeta();
        if (itemMeta == null) return this;
        itemMeta.setDamage(damage);
        this.getItem().setItemMeta(itemMeta);
        return this;
    }
    
    public BukkitItemStack setCustomModelData(int customModelData) {
        var itemMeta = this.getItemMeta();
        if (itemMeta == null) return this;
        itemMeta.setCustomModelData(customModelData);
        this.getItem().setItemMeta(itemMeta);
        return this;
    }
    
    public BukkitItemStack setOwningPlayer(String owningPlayer) {
        var uuid = UUIDUtil.from(owningPlayer);
        if (uuid == null) return this;
        var itemMeta = (SkullMeta) this.getItemMeta();
        if (itemMeta == null) return this;
        if (NmsVersion.getCurrentVersion().isNewHead()) {
            var offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            itemMeta.setOwningPlayer(offlinePlayer);
        } else itemMeta.setOwner(owningPlayer);
        this.getItem().setItemMeta(itemMeta);
        return this;
    }
    
    public BukkitItemStack setOwningPlayer(OfflinePlayer owningPlayer) {
        return setOwningPlayer(owningPlayer.getName());
    }
    
    public BukkitItemStack setUnbreakable(boolean isUnbreakable) {
        var itemMeta = this.getItemMeta();
        if (itemMeta == null) return this;
        itemMeta.setUnbreakable(isUnbreakable);
        this.getItem().setItemMeta(itemMeta);
        return this;
    }
    
    public BukkitItemStack setLeatherColor(Color leatherColor) {
        var type = this.getItem().getType();
        if (!(type == Material.LEATHER_HELMET || type == Material.LEATHER_CHESTPLATE || type == Material.LEATHER_LEGGINGS || type == Material.LEATHER_BOOTS))
            throw new IllegalArgumentException("color(Color) only applicable for leather armor.");
        var itemMeta = (LeatherArmorMeta) this.getItemMeta();
        if (itemMeta == null) return this;
        itemMeta.setColor(leatherColor);
        this.getItem().setItemMeta(itemMeta);
        return this;
    }
    
    public BukkitItemStack setItemFlags(ItemFlag... itemFlags) {
        var itemMeta = this.getItemMeta();
        if (itemMeta == null) return this;
        itemMeta.getItemFlags().forEach(itemMeta::removeItemFlags);
        itemMeta.addItemFlags(itemFlags);
        this.getItem().setItemMeta(itemMeta);
        return this;
    }
    
    public BukkitItemStack setEnchantments(EnchantData... enchantments) {
        var itemMeta = this.getItemMeta();
        if (itemMeta == null) return this;
        Arrays.stream(enchantments).forEach(enchant -> itemMeta.addEnchant(
                enchant.getEnchantment(), enchant.getStage(), enchant.isState()
        ));
        this.getItem().setItemMeta(itemMeta);
        return this;
    }
    
    public BukkitItemStack setAttributes(AttributeData... attributes) {
        var itemMeta = this.getItemMeta();
        if (itemMeta == null) return this;
        Arrays.stream(attributes).forEach(attribute -> itemMeta.addAttributeModifier(
                attribute.getAttribute(), attribute.getAttributeModifier()
        ));
        this.getItem().setItemMeta(itemMeta);
        return this;
    }

    public ItemMeta getItemMeta() {
        return getItem().getItemMeta();
    }

    public static BukkitItemStack makeItemStack(Material src) {
        return new BukkitItemStack().setMaterial(src);
    }

    public static BukkitItemStack makeItemStack(ItemStack src) {
        return new BukkitItemStack().setItem(src);
    }
}