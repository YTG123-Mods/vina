@file:JvmName("WprImpl")

package io.github.ytg1234.vina.impl.mixin

import io.github.ytg1234.vina.impl.api.EntityWeighable
import net.minecraft.entity.Entity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.MathHelper.clamp
import net.minecraft.world.World

fun weigh(world: World, pos: BlockPos, box: Box): Int {
    val entities = world.getNonSpectatingEntities(Entity::class.java, box.offset(pos))
    val weightList = entities.map { EntityWeighable(it).weight }
    val total = clamp(weightList.sum(), 0, 15)

    return total
}
