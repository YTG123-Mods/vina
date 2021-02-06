package io.github.ytg1234.vina.impl.api.trait

import io.github.ytg1234.vina.api.WeightManager
import io.github.ytg1234.vina.api.trait.Weighable
import io.github.ytg1234.vina.config
import net.minecraft.block.BlockEntityProvider
import net.minecraft.entity.Entity
import net.minecraft.entity.ItemEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.inventory.Inventory
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.MathHelper
import org.jetbrains.annotations.Range
import kotlin.math.min

internal class EntityWeighable(private val ett: Entity) : Weighable {
    override val weight: @Range(from = 0, to = 15) Int
        get() {
            var initialWeight = WeightManager[ett.type]
            if (ett is ItemEntity) {
                initialWeight = handleItems(ett, initialWeight = initialWeight)
            } else if (ett is LivingEntity && ett.hasStatusEffect(StatusEffects.SLOWNESS) && config.entityWeight.isSlownessAddedToEntityWeight) {
                initialWeight += ett.getStatusEffect(StatusEffects.SLOWNESS)!!.amplifier + 1
            }
            return MathHelper.clamp(initialWeight, 0, 15)
        }
}

internal fun handleItems(vararg items: ItemEntity, initialWeight: Int): Int {
    fun calcFromStacks(vararg stacks: ItemStack): Int {
        return initialWeight + calculateRedstoneSignal(DefaultedList.copyOf(ItemStack.EMPTY, *stacks))
    }

    return if (items.size == 1) {
        val ett = items[0]
        if (ett.stack.item is BlockItem && (ett.stack.orCreateTag.contains("BlockEntityTag") || ett.stack.orCreateTag.contains(
                "Items"
            )) && config.entityWeight.isInventoryAddedToItemWeight
        ) {
            initialWeight + calculateRedstoneSignal(
                fromTagButGood(
                    if (ett.stack.orCreateTag.contains("BlockEntityTag")) ett.stack.orCreateTag.getCompound(
                        "BlockEntityTag"
                    ) else ett.stack.orCreateTag,
                    run {
                        if ((ett.stack.item as BlockItem).block.hasBlockEntity()) {
                            val be =
                                ((ett.stack.item as BlockItem).block as BlockEntityProvider).createBlockEntity(
                                    ett.world
                                )
                            if (be is Inventory) be.size()
                        }
                        27
                    }
                )
            )
        } else if (config.entityWeight.isStackSizeAddedToItemWeight) {
            calcFromStacks(ett.stack)
        } else initialWeight
    } else {
        if (items.any {
                it.stack.item is BlockItem && (it.stack.orCreateTag.contains("BlockEntityTag") || it.stack.orCreateTag.contains(
                    "Items"
                ))
            }) items.sumOf { handleItems(it, initialWeight = initialWeight) }
        else initialWeight + calcFromStacks(*items.map { it.stack }.toTypedArray())
    }
}

internal fun fromTagButGood(tag: CompoundTag, size: Int): DefaultedList<ItemStack> {
    val ls = DefaultedList.ofSize(size, ItemStack.EMPTY)
    val listTag = tag.getList("Items", 10)

    listTag.forEachIndexed { idx, nbt ->
        ls[idx] = ItemStack.fromTag(nbt as CompoundTag)
    }

    return ls
}

internal fun calculateRedstoneSignal(inventory: DefaultedList<ItemStack>): Int {
    val filledStacks = inventory.filter { !it.isEmpty }
    val total = filledStacks.fold(0.0) { acc, stack ->
        acc + stack.count.toDouble() / min(64, stack.maxCount).toDouble()
    }
    return MathHelper.floor((total / inventory.size.toDouble()) * 13.0) + if (filledStacks.isNotEmpty()) 1 else 0
}
