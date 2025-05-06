package dev.lone64.LoneLib.common.itemstack.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.enchantments.Enchantment;

@Getter
@AllArgsConstructor
public class EnchantData {
    private final Enchantment enchantment;
    private final int stage;
    private final boolean state;

    public EnchantData(Enchantment enchantment) {
        this(enchantment, 1);
    }
    public EnchantData(Enchantment enchantment, int stage) {
        this(enchantment, stage, false);
    }
}