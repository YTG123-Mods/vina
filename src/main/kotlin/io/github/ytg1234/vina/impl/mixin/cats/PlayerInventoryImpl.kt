@file:JvmName("PIImpl")

package io.github.ytg1234.vina.impl.mixin.cats

import io.github.ytg1234.vina.impl.api.ItemStackDroppedItem
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack

internal fun transformStack(player: PlayerEntity, i: Int, stack: ItemStack) {
    if (!player.world.isClient) {
        val item = ItemStackDroppedItem(player.uuid, i in 1..8)
        stack.tag = item.writeToNbt(stack.orCreateTag)
    }
}
