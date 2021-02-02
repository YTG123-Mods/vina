package io.github.ytg1234.vina.api.trait

import io.github.ytg1234.vina.impl.api.ItemStackDroppedItem
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.Util
import org.jetbrains.annotations.Contract
import java.util.UUID

interface DroppedItem : CanStoreNbt {
    fun getOwner(): UUID
    fun isHotbar(): Boolean

    companion object {
        @JvmStatic
        @Contract(value = "_->new", pure = true)
        fun fromTag(tag: CompoundTag): DroppedItem {
            val initial = ItemStackDroppedItem(Util.NIL_UUID, false)
            initial.readFromNbt(tag)
            return initial
        }

        @JvmStatic
        @Contract(value = "_->new", pure = true)
        fun fromStack(stack: ItemStack) = fromTag(stack.orCreateTag)
    }
}
