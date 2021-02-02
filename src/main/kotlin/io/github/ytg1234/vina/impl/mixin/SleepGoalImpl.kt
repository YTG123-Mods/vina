@file:JvmName("SlpGlImpl")

package io.github.ytg1234.vina.impl.mixin

import io.github.ytg1234.vina.Components
import io.github.ytg1234.vina.MOD_ID
import io.github.ytg1234.vina.api.DroppedItem.Companion.fromStack
import io.github.ytg1234.vina.config
import net.minecraft.entity.Entity
import net.minecraft.entity.ItemEntity
import net.minecraft.world.World
import java.util.Random
import java.util.stream.Collectors

fun lostItems(self: World, ett: Entity): Boolean {
    if (!self.isClient && config.godCatsConfig.isEnabled) {
        val random = Random() // Not interrupt the previous one, for RNG manipulation needs
        val bl = (config.godCatsConfig.chanceToReturnLostItem == 1 ||
                random.nextInt(config.godCatsConfig.chanceToReturnLostItem - 1) < 1) && config.godCatsConfig.chanceToReturnLostItem >= 1

        if (bl) {
            var i = 0
            while (i < Components.DROPPED_ITEMS.get(self.levelProperties).droppedItems.size) {
                if (
                    !Components.DROPPED_ITEMS.get(self.levelProperties).droppedItems[i].orCreateTag.contains("$MOD_ID:owner") ||
                    !Components.DROPPED_ITEMS.get(self.levelProperties).droppedItems[i].orCreateTag.contains("$MOD_ID:isHotbar")
                ) {
                    Components.DROPPED_ITEMS.get(self.levelProperties).removeStack(i)
                }
                i++
            }

            val stacks =
                Components.DROPPED_ITEMS.get(self.levelProperties).droppedItems.stream().filter {
                    if (!it.orCreateTag.contains("$MOD_ID:owner") || !it.orCreateTag
                            .contains("$MOD_ID:isHotbar")
                    ) {
                        return@filter false
                    }
                    if (!fromStack(it).isHotbar()) {
                        if (config.godCatsConfig.hotbarBias == 1) return@filter false
                        if (config.godCatsConfig.hotbarBias < 1) return@filter true
                        return@filter random.nextInt(config.godCatsConfig.hotbarBias - 1) > 0
                    }
                    true
                }.collect(Collectors.toList())
            if (stacks.size > 0) {
                val chosen = stacks[random.nextInt(stacks.size)]
                Components.DROPPED_ITEMS.get(self.levelProperties).removeStack(chosen)
                (ett as ItemEntity).stack = chosen
            }
        }
    }
    return self.spawnEntity(ett)
}
