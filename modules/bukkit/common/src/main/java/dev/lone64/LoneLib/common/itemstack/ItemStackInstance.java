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
public class ItemStackInstance {
    private ItemStack itemStack;

    public ItemStackInstance setMaterial(Material material) {
        this.itemStack = new ItemStack(material, 1);
        return this;
    }
    
    public ItemStackInstance setItem(ItemStack item) {
        this.itemStack = item;
        return this;
    }
    
    public ItemStackInstance setAmount(int amount) {
        this.getItemStack().setAmount(amount);
        return this;
    }
    
    public ItemStackInstance setName(String name) {
        var itemMeta = this.getItemMeta();
        if (itemMeta == null) return this;
        itemMeta.setDisplayName(format(name));
        this.getItemStack().setItemMeta(itemMeta);
        return this;
    }
    
    public ItemStackInstance setLore(List<String> lore) {
        var itemMeta = this.getItemMeta();
        if (itemMeta == null) return this;
        itemMeta.setLore(format(lore));
        this.getItemStack().setItemMeta(itemMeta);
        return this;
    }
    
    public ItemStackInstance setDamage(int damage) {
        var itemMeta = (Damageable) this.getItemMeta();
        if (itemMeta == null) return this;
        itemMeta.setDamage(damage);
        this.getItemStack().setItemMeta(itemMeta);
        return this;
    }
    
    public ItemStackInstance setCustomModelData(int customModelData) {
        var itemMeta = this.getItemMeta();
        if (itemMeta == null) return this;
        itemMeta.setCustomModelData(customModelData);
        this.getItemStack().setItemMeta(itemMeta);
        return this;
    }
    
    public ItemStackInstance setOwningPlayer(String owningPlayer) {
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
    
    public ItemStackInstance setOwningPlayer(OfflinePlayer owningPlayer) {
        return setOwningPlayer(owningPlayer.getName());
    }
    
    public ItemStackInstance setUnbreakable(boolean isUnbreakable) {
        var itemMeta = this.getItemMeta();
        if (itemMeta == null) return this;
        itemMeta.setUnbreakable(isUnbreakable);
        this.getItemStack().setItemMeta(itemMeta);
        return this;
    }
    
    public ItemStackInstance setLeatherColor(Color leatherColor) {
        var type = this.getItemStack().getType();
        if (!(type == Material.LEATHER_HELMET || type == Material.LEATHER_CHESTPLATE || type == Material.LEATHER_LEGGINGS || type == Material.LEATHER_BOOTS))
            throw new IllegalArgumentException("color(Color) only applicable for leather armor.");
        var itemMeta = (LeatherArmorMeta) this.getItemMeta();
        if (itemMeta == null) return this;
        itemMeta.setColor(leatherColor);
        this.getItemStack().setItemMeta(itemMeta);
        return this;
    }
    
    public ItemStackInstance setItemFlags(ItemFlag... itemFlags) {
        var itemMeta = this.getItemMeta();
        if (itemMeta == null) return this;
        itemMeta.getItemFlags().forEach(itemMeta::removeItemFlags);
        itemMeta.addItemFlags(itemFlags);
        this.getItemStack().setItemMeta(itemMeta);
        return this;
    }
    
    public ItemStackInstance setEnchantments(EnchantData... enchantments) {
        var itemMeta = this.getItemMeta();
        if (itemMeta == null) return this;
        Arrays.stream(enchantments).forEach(enchant -> itemMeta.addEnchant(
                enchant.getEnchantment(), enchant.getStage(), enchant.isState()
        ));
        this.getItemStack().setItemMeta(itemMeta);
        return this;
    }
    
    public ItemStackInstance setAttributes(AttributeData... attributes) {
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

    public static ItemStackInstance loadItem(Material src) {
        return new ItemStackInstance().setMaterial(src);
    }
    public static ItemStackInstance loadItem(ItemStack src) {
        return new ItemStackInstance().setItem(src);
    }
}