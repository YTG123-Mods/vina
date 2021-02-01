package io.github.ytg1234.vina.api.component

import dev.onyxstudios.cca.api.v3.component.Component
import java.util.UUID

interface ItemDropItemComponent : Component {
    fun getOwner(): UUID
    fun isHotbar(): Boolean
}
