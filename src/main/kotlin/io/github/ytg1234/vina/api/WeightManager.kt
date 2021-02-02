package io.github.ytg1234.vina.api

import io.github.ytg1234.vina.impl.api.WeightManagerImpl
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener
import net.minecraft.entity.EntityType
import org.jetbrains.annotations.Range

interface WeightManager : SimpleResourceReloadListener<Map<EntityType<*>, @Range(from = 0, to = 15) Int>> {
    operator fun get(type: EntityType<*>): @Range(from = 0, to = 15) Int

    companion object : WeightManager by WeightManagerImpl
}
