@file:Suppress("UnstableApiUsage")

package io.github.ytg1234.vina

import dev.onyxstudios.cca.api.v3.component.ComponentKey
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry
import dev.onyxstudios.cca.api.v3.item.ItemComponentFactoryRegistry
import dev.onyxstudios.cca.api.v3.item.ItemComponentInitializer
import dev.onyxstudios.cca.api.v3.level.LevelComponentFactoryRegistry
import dev.onyxstudios.cca.api.v3.level.LevelComponentInitializer
import io.github.ytg1234.vina.api.component.DroppedItemLevel
import io.github.ytg1234.vina.api.component.ItemDropItemComponent
import io.github.ytg1234.vina.impl.api.component.DroppedItemLevelImpl
import io.github.ytg1234.vina.impl.api.component.ItemDropItemComponentImpl
import net.minecraft.util.Identifier

object Components : ItemComponentInitializer, LevelComponentInitializer {
    @JvmField
    val ITEM_DROP: ComponentKey<ItemDropItemComponent> = ComponentRegistry.getOrCreate(Identifier(MOD_ID, "item_drop"), ItemDropItemComponent::class.java)

    @JvmField
    val DROPPED_ITEMS: ComponentKey<DroppedItemLevel> = ComponentRegistry.getOrCreate(Identifier(MOD_ID, "dropped_items"), DroppedItemLevel::class.java)

    override fun registerItemComponentFactories(registry: ItemComponentFactoryRegistry) {
//        registry.register({ true }, ITEM_DROP, ::ItemDropItemComponentImpl)
    }

    override fun registerLevelComponentFactories(registry: LevelComponentFactoryRegistry) {
        registry.register(DROPPED_ITEMS, ::DroppedItemLevelImpl)
    }
}
