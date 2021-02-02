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

class EntityWeighable(private val ett: Entity) : Weighable {
    override val weight: @Range(from = 0, to = 15) Int
        get() {
            var initialWeight = WeightManager[ett.type]
            if (ett is ItemEntity && ett.stack.item is BlockItem && ett.stack.orCreateTag.contains("Items") && config.entityWeightConfig.isInventoryAddedToItemWeight) {
                initialWeight += fromTagButGood(ett.stack.orCreateTag).size
            } else if (ett is LivingEntity && ett.hasStatusEffect(StatusEffects.SLOWNESS) && config.entityWeightConfig.isSlownessAddedToEntityWeight) {
                initialWeight += ett.getStatusEffect(StatusEffects.SLOWNESS)!!.amplifier + 1
            }
            return MathHelper.clamp(initialWeight, 0, 15)
        }
}

fun fromTagButGood(tag: CompoundTag): List<ItemStack> {
    val ls = mutableListOf<ItemStack>()
    val listTag = tag.getList("Items", 10)

    listTag.forEach {
        ls.add(ItemStack.fromTag(it as CompoundTag))
    }

    return ls
}
