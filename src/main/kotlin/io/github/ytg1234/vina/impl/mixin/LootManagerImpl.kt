@file:JvmName("LMImpl")

package io.github.ytg1234.vina.impl.mixin

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import io.github.ytg1234.vina.Vina
import io.github.ytg1234.vina.config
import net.minecraft.util.Identifier
import java.io.InputStreamReader

fun addGilded(map: MutableMap<Identifier, JsonElement>) {
    if (config.isGildedLootTableEnabled) {
        (Vina::class.java.classLoader
            .getResourceAsStream("loader/gilded_blackstone.json")
            ?: throw NullPointerException("Could not find gilded blackstone loot table!")
                ).use { stream ->
                InputStreamReader(stream).use {
                    map[Identifier("minecraft", "blocks/gilded_blackstone")] =
                        Gson().fromJson(it, JsonObject::class.java)
                }
            }
    }
}
