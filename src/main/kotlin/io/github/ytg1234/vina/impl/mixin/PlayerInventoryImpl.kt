@file:JvmName("PIImpl")

package io.github.ytg1234.vina.impl.mixin

import io.github.ytg1234.vina.impl.api.ItemStackDroppedItem
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import org.jetbrains.annotations.ApiStatus

internal fun transformStack(player: PlayerEntity, i: Int, stack: ItemStack) {
    if (!player.world.isClient) {
        val item = ItemStackDroppedItem(player.uuid, i in 1..8)
        stack.tag = item.writeToNbt(stack.orCreateTag)
    }
}
