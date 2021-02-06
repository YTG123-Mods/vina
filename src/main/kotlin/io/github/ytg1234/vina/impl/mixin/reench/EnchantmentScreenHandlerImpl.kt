@file:JvmName("ESHImpl")

package io.github.ytg1234.vina.impl.mixin.reench

import io.github.ytg1234.vina.config
import net.minecraft.enchantment.Enchantment
import net.minecraft.item.EnchantedBookItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

internal fun ItemStack.allowEnchBooks(): Item {
    return if (config.reenchConfig.isEnabled &&
        item === Items.ENCHANTED_BOOK &&
        config.reenchConfig.isEnchantBook
    ) Items.BOOK else item
}

@Suppress("UNCHECKED_CAST")
internal fun ItemStack.fixConflict(toAdd: Enchantment, level: Int) {
    if (config.reenchConfig.isEnabled) {
        var add = true
        val enchantments = if (item === Items.BOOK) EnchantedBookItem.getEnchantmentTag(this) else enchantments
        for (enchantment in enchantments as Iterable<CompoundTag>) { // Iterating over all enchantments
            if (Identifier(enchantment.getString("id")) == Registry.ENCHANTMENT.getId(toAdd)) { // If it's the same ench, we need to merge 'em
                if (config.reenchConfig.isLevelOverrideWhenGreater) {
                    if (level > enchantment.getInt("lvl")) {
                        enchantment.putInt("lvl", level)
                    } else if (level == enchantment.getInt("lvl")) {
                        enchantment.putInt("lvl", (level + 1).coerceAtMost(toAdd.maxLevel))
                    }
                }
                add = false
            } else if (!toAdd.canCombine(Registry.ENCHANTMENT[Identifier(enchantment.getString("id"))])) { // If not, we need to cancel the operation if they cannot be combined
                return
            }
        }
        if (add) addEnchantment(toAdd, level)
    } else addEnchantment(toAdd, level)
}
