package dev.lone64.LoneLib.common.itemstack.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;

@Getter
@AllArgsConstructor
public class AttributeData {
    private final Attribute attribute;
    private final AttributeModifier attributeModifier;
}