@file:Suppress("UnstableApiUsage")

package io.github.ytg1234.vina

import dev.onyxstudios.cca.api.v3.component.ComponentKey
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry
import dev.onyxstudios.cca.api.v3.level.LevelComponentFactoryRegistry
import dev.onyxstudios.cca.api.v3.level.LevelComponentInitializer
import io.github.ytg1234.vina.api.component.DroppedItemLevel
import io.github.ytg1234.vina.impl.api.component.DroppedItemLevelImpl
import net.minecraft.util.Identifier

object Components : LevelComponentInitializer {
    @JvmField
    val DROPPED_ITEMS: ComponentKey<DroppedItemLevel> = ComponentRegistry.getOrCreate(Identifier(MOD_ID, "dropped_items"), DroppedItemLevel::class.java)

    override fun registerLevelComponentFactories(registry: LevelComponentFactoryRegistry) {
        registry.register(DROPPED_ITEMS, ::DroppedItemLevelImpl)
    }
}
