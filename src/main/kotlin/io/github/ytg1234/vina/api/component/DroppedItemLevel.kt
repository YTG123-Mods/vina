package io.github.ytg1234.vina.api.component

import dev.onyxstudios.cca.api.v3.component.Component
import net.minecraft.item.ItemStack
import org.jetbrains.annotations.Contract

interface DroppedItemLevel : Component {
    val droppedItems: List<ItemStack>
        @Contract(pure = true) get

    fun addStack(stack: ItemStack)
    fun removeStack(stack: ItemStack)
    fun removeStack(index: Int)
}
