@file:JvmName("EHImpl")

package io.github.ytg1234.vina.impl.mixin.reench

import io.github.ytg1234.vina.config
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.enchantment.EnchantmentLevelEntry
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

internal fun removeConflicts0(
    possibleEnchs: List<EnchantmentLevelEntry>,
    stack: ItemStack
): List<EnchantmentLevelEntry> {
    return if (config.reenchConfig.isEnabled) {
        possibleEnchs.asSequence().filter { entry ->  // filter the conflicts away
            val e = entry.enchantment
            stack.enchantments.all { // we want to remove IF AND ONLY IF the enchant can't combine AND is not the same
                val inTag = Registry.ENCHANTMENT[Identifier(
                    (it as CompoundTag).getString("id")
                )]
                e == inTag || e.canCombine(inTag)
            }
        }.toList()
    } else possibleEnchs
}

internal fun removeConflicts2(possibleEntries: List<EnchantmentLevelEntry>, picked: EnchantmentLevelEntry): List<EnchantmentLevelEntry> {
    var possibleEntries2 = possibleEntries
    if (config.reenchConfig.isEnabled) {
        possibleEntries2 = possibleEntries2.filter { it.enchantment != picked.enchantment }
    }
    EnchantmentHelper.removeConflicts(possibleEntries2, picked)
    return possibleEntries2
}

internal val maxBookshelves: Int
    get() = if (config.reenchConfig.isEnabled) config.reenchConfig.maxAllowedBookshelves else 15
