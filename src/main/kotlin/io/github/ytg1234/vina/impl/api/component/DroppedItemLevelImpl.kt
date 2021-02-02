package io.github.ytg1234.vina.impl.api.component

import io.github.ytg1234.vina.MOD_ID
import io.github.ytg1234.vina.api.component.DroppedItemLevel
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.world.WorldProperties

class DroppedItemLevelImpl(val props: WorldProperties) : DroppedItemLevel {
    override var droppedItems: List<ItemStack> = listOf()
        private set

    override fun addStack(stack: ItemStack) {
        val ls = droppedItems.toMutableList()
        ls.add(stack)
        droppedItems = ls.toList()
    }

    override fun removeStack(stack: ItemStack) {
        val ls = droppedItems.toMutableList()
        val it = ls.iterator()
        for (i in it) {
            if (i.hashCode() == stack.hashCode() || i === stack) it.remove()
        }
        droppedItems = ls.toList()
    }

    override fun removeStack(index: Int) {
        val ls = droppedItems.toMutableList()
        ls.removeAt(index)
        droppedItems = ls.toList()
    }

    override fun readFromNbt(tag: CompoundTag) {
        val list = mutableListOf<ItemStack>()
        if (tag.contains("$MOD_ID:DroppedItems")) {
            list.addAll(tag.getList("$MOD_ID:DroppedItems", 10).map { ItemStack.fromTag(it as CompoundTag) })
        }
        droppedItems = list.toList()
    }

    override fun writeToNbt(tag: CompoundTag) {
        val list = ListTag()
        droppedItems.forEach { list.add(it.toTag(CompoundTag())) }
        tag.put("$MOD_ID:DroppedItems", list)
    }
}
