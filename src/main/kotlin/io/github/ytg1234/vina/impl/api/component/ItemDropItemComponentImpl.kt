package io.github.ytg1234.vina.impl.api.component

import dev.onyxstudios.cca.api.v3.item.ItemComponent
import io.github.ytg1234.vina.api.component.ItemDropItemComponent
import net.minecraft.item.ItemStack
import net.minecraft.util.Util
import java.util.UUID

class ItemDropItemComponentImpl(stack: ItemStack) : ItemComponent(stack), ItemDropItemComponent {
    override var isHotbar: Boolean
        get() = getBoolean("isHotbar")
        set(value) = putBoolean("isHotbar", value)

    override var owner: UUID
        get() {
            if (rootTag == null) return DEFAULT_OWNER
            if (!hasTag("owner")) return run { rootTag!!.putUuid("owner", DEFAULT_OWNER); DEFAULT_OWNER }
            return rootTag!!.getUuid("owner")
        }
        set(value) = orCreateRootTag.putUuid("owner", value)

    companion object {
        private val DEFAULT_HOTBAR = false
        private val DEFAULT_OWNER = Util.NIL_UUID
    }
}
