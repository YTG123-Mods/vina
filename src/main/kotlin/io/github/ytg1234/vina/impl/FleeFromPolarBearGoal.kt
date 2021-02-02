package io.github.ytg1234.vina.impl

import io.github.ytg1234.vina.config
import net.minecraft.entity.ai.goal.EscapeDangerGoal
import net.minecraft.entity.mob.PathAwareEntity
import net.minecraft.entity.passive.PolarBearEntity

// What's funny is that I call it flee even though it literally freezes the entity
class FleeFromPolarBearGoal(mob: PathAwareEntity, speed: Double) : EscapeDangerGoal(mob, speed) {
    override fun canStart(): Boolean {
        if (mob.world.getNonSpectatingEntities(PolarBearEntity::class.java, mob.boundingBox.expand(config.usefulPolarBears.polarBearRange))
                .isNotEmpty()
        ) {
            targetX = mob.x
            targetY = mob.y
            targetZ = mob.z
            return true
        }
        return false
    }

    override fun canStop(): Boolean {
        return !canStart()
    }
}
