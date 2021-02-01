package io.github.ytg1234.vina.api

import java.util.UUID

interface DroppedItem : CanStoreNbt {
    fun getOwner(): UUID
    fun isHotbar(): Boolean
}
