@file:JvmName("WprImpl")

package io.github.ytg1234.vina.impl.mixin

import io.github.ytg1234.vina.api.WeightManager
import io.github.ytg1234.vina.impl.api.trait.EntityWeighable
import io.github.ytg1234.vina.impl.api.trait.handleItems
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.ItemEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.MathHelper.clamp
import net.minecraft.world.World

internal fun weigh(world: World, pos: BlockPos, box: Box): Int {
    val entities = world.getNonSpectatingEntities(Entity::class.java, box.offset(pos))
    if (entities.isEmpty()) return 0
    val weightList = if (entities.all { it is ItemEntity }) listOf(
        handleItems(
            *entities.map { it as ItemEntity }.toTypedArray(),
            initialWeight = WeightManager[EntityType.ITEM]
        ) - 1
    ) else entities.map { EntityWeighable(it).weight }
    val total = clamp(weightList.sum(), 0, 15)

    return total
}
