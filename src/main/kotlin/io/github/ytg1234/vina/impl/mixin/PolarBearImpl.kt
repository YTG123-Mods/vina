@file:JvmName("PBImpl")

package io.github.ytg1234.vina.impl.mixin

import io.github.ytg1234.vina.config
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.TargetPredicate
import net.minecraft.entity.ai.goal.AnimalMateGoal
import net.minecraft.entity.ai.goal.GoalSelector
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.passive.PolarBearEntity
import net.minecraft.util.math.Box
import net.minecraft.world.World
import java.util.function.Consumer

fun AnimalEntity.initGoals(s: GoalSelector) {
    if (config.usefulPolarBears.isBreedingEnabled) {
        s.add(3, AnimalMateGoal(this, config.usefulPolarBears.breedingChance))
    }
}

fun Entity.onKilledOther(
    world: World,
    ett: LivingEntity,
    breedingAge: Int,
    canEat: Boolean,
    setLoveTicks: Consumer<Int>
) {
    if (!world.isClient &&
        ett is AnimalEntity &&
        ett !is PolarBearEntity &&
        breedingAge == 0 &&
        canEat &&
        config.usefulPolarBears.isBreedingEnabled
    ) {
        setLoveTicks.accept(config.usefulPolarBears.loveTicks)
        if (config.usefulPolarBears.isHeartShown) {
            world.sendEntityStatus(this, 18.toByte())
        }
    }
}

fun LivingEntity.tick(
    world: World,
    loveTicks: Int,
    x: Double,
    y: Double,
    z: Double,
    box: Box,
    canTarget: (AnimalEntity) -> Boolean,
    setTarget: Consumer<AnimalEntity>
) {
    if (!world.isClient && loveTicks <= 0 && config.usefulPolarBears.isBreedingEnabled) {
        val closest = world.getClosestEntity(
            AnimalEntity::class.java,
            TargetPredicate().setPredicate { e: LivingEntity? -> e !is PolarBearEntity },
            this,
            x,
            y,
            z,
            box.expand(8.0, 4.0, 0.0)
        )
        if (closest != null && canTarget(closest)) {
            setTarget.accept(closest)
        }
    }
}
