package io.github.ytg1234.vina.impl.api.component

import io.github.ytg1234.vina.api.component.ItemDropItemComponent
import net.minecraft.nbt.CompoundTag
import java.util.UUID

data class ItemDropItemComponentImpl(private var owner: UUID, private var isHotbar: Boolean) : ItemDropItemComponent {
    override fun getOwner() = owner
    override fun isHotbar() = isHotbar

    override fun readFromNbt(tag: CompoundTag) {
        owner = tag.getUuid("vina\$droppedItemOwner")
        isHotbar = tag.getBoolean("vina\$droppedItemFromHotbar")
    }

    override fun writeToNbt(tag: CompoundTag) {
        tag.putUuid("vina\$droppedItemOwner", owner)
        tag.putBoolean("vina\$droppedFromHotbar", isHotbar)
    }
}
