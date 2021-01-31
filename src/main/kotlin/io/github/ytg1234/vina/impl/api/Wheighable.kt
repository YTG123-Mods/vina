package io.github.ytg1234.vina.impl.api

import io.github.ytg1234.vina.api.Weighable
import io.github.ytg1234.vina.impl.WeightManager
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.util.math.MathHelper
import org.jetbrains.annotations.Range

class EntityWeighable(private val ett: Entity) : Weighable {
    override val weight: @Range(from = 0, to = 15) Int
        get() {
            var initialWeight = WeightManager[ett.type]
            if (ett is LivingEntity) {
                if (ett.hasStatusEffect(StatusEffects.SLOWNESS)) {
                    initialWeight += ett.getStatusEffect(StatusEffects.SLOWNESS)!!.amplifier + 1
                }
            }
            return MathHelper.clamp(initialWeight, 0, 15)
        }
}
