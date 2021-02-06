package io.github.ytg1234.vina.impl.api

import io.github.ytg1234.vina.MOD_ID
import io.github.ytg1234.vina.api.trait.DroppedItem
import net.minecraft.nbt.CompoundTag
import java.util.UUID

internal class ItemStackDroppedItem(private var owner: UUID, private var isHotbar: Boolean) : DroppedItem {
    override fun getOwner() = owner

    override fun isHotbar() = isHotbar

    override fun readFromNbt(nbt: CompoundTag) {
        owner = nbt.getUuid("$MOD_ID:owner")
        isHotbar = nbt.getBoolean("$MOD_ID:isHotbar")
    }

    override fun writeToNbt(nbt: CompoundTag): CompoundTag {
        nbt.putUuid("$MOD_ID:owner", owner)
        nbt.putBoolean("$MOD_ID:isHotbar", isHotbar)
        return nbt
    }
}
