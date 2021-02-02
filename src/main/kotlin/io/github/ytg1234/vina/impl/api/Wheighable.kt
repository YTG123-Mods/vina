package io.github.ytg1234.vina.impl.api

import io.github.ytg1234.vina.api.Weighable
import io.github.ytg1234.vina.config
import io.github.ytg1234.vina.impl.WeightManager
import net.minecraft.entity.Entity
import net.minecraft.entity.ItemEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.math.MathHelper
import org.jetbrains.annotations.Range
import kotlin.math.min

class EntityWeighable(private val ett: Entity) : Weighable {
    override val weight: @Range(from = 0, to = 15) Int
        get() {
            var initialWeight = WeightManager[ett.type]
            if (ett is ItemEntity && ett.stack.item is BlockItem && (ett.stack.orCreateTag.contains("BlockEntityTag") || ett.stack.orCreateTag.contains(
                    "Items"
                )) && config.entityWeightConfig.isInventoryAddedToItemWeight
            ) {
                initialWeight += calculateRedstoneSignal(
                    fromTagButGood(
                        if (ett.stack.orCreateTag.contains("BlockEntityTag")) ett.stack.orCreateTag.getCompound(
                            "BlockEntityTag"
                        ) else ett.stack.orCreateTag
                    )
                )
            } else if (ett is LivingEntity && ett.hasStatusEffect(StatusEffects.SLOWNESS) && config.entityWeightConfig.isSlownessAddedToEntityWeight) {
                initialWeight += ett.getStatusEffect(StatusEffects.SLOWNESS)!!.amplifier + 1
            }
            return MathHelper.clamp(initialWeight, 0, 15)
        }
}

internal fun fromTagButGood(tag: CompoundTag): List<ItemStack> {
    val ls = mutableListOf<ItemStack>()
    val listTag = tag.getList("Items", 10)

    listTag.forEach {
        ls.add(ItemStack.fromTag(it as CompoundTag))
    }

    return ls
}

internal fun calculateRedstoneSignal(inventory: List<ItemStack>): Int {
    val filledStacks = inventory.filter { !it.isEmpty }
    val total = filledStacks.fold(0.0) { acc, stack ->
        acc + stack.count.toDouble() / min(64, stack.maxCount).toDouble()
    }
    return MathHelper.floor((total / inventory.size.toDouble()) * 13.0) + if (filledStacks.isNotEmpty()) 1 else 0
}
