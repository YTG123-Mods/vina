package io.github.ytg1234.vina.api

import net.minecraft.nbt.CompoundTag
import org.jetbrains.annotations.Contract

/**
 * Can store Named Binary Tag data using a [CompoundTag].
 *
 * @author YTG1234
 */
interface CanStoreNbt {
    @Contract(mutates = "this")
    fun readFromNbt(nbt: CompoundTag)

    @Contract(value = "_->param1", mutates = "param1")
    fun writeToNbt(nbt: CompoundTag): CompoundTag
}
