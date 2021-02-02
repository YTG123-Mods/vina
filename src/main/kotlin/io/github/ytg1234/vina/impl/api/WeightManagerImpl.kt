package io.github.ytg1234.vina.impl.api

import com.google.gson.Gson
import com.google.gson.JsonParser
import io.github.ytg1234.vina.MOD_ID
import io.github.ytg1234.vina.api.WeightManager
import net.minecraft.entity.EntityType
import net.minecraft.resource.ResourceManager
import net.minecraft.util.Identifier
import net.minecraft.util.math.MathHelper.clamp
import net.minecraft.util.profiler.Profiler
import net.minecraft.util.registry.Registry
import org.jetbrains.annotations.Range
import java.util.Locale
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor

object WeightManagerImpl : WeightManager {
    override fun getFabricId() = Identifier(MOD_ID, "weight")

    @Volatile
    private var weightMap: Map<EntityType<*>, @Range(from = 0, to = 15) Int> = mapOf()

    private val gson = Gson()

    override fun load(
        manager: ResourceManager,
        profiler: Profiler,
        executor: Executor
    ): CompletableFuture<Map<EntityType<*>, Int>> {
        return CompletableFuture.supplyAsync({
            profiler.startTick()
            profiler.push("WeightManager")

            val resources = manager.findResources("weight") { it == "weight.json" }

            val pairs = mutableListOf<Pair<EntityType<*>, Int>>()

            for (id in resources) {
                val jsonObj = manager.getResource(id).use { JsonParser().parse(it.inputStream.reader()).asJsonObject }

                jsonObj.entrySet().forEach {
                    if (it.key.toLowerCase(Locale.ENGLISH) == "_comment") return@forEach

                    pairs.add(
                        Registry.ENTITY_TYPE.get(
                            Identifier(
                                id.namespace,
                                it.key.toLowerCase(Locale.ENGLISH)
                            )
                        ) to clamp(it.value.asInt, 0, 15)
                    )
                }
            }

            val map = mapOf(*pairs.toTypedArray())
            profiler.pop()
            map
        }, executor)
    }

    override fun apply(
        data: Map<EntityType<*>, Int>,
        manager: ResourceManager,
        profiler: Profiler,
        executor: Executor
    ): CompletableFuture<Void> {
        return CompletableFuture.runAsync({
            weightMap = data
        }, executor)
    }

    override operator fun get(type: EntityType<*>): @Range(from = 0, to = 15) Int = weightMap[type] ?: 1
}
