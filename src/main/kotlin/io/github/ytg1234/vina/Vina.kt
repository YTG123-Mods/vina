package io.github.ytg1234.vina

import com.mojang.brigadier.Command
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry
import io.github.ytg1234.vina.api.component.ItemDropItemComponent
import io.github.ytg1234.vina.impl.WeightManager
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.discovery.ModResolutionException
import net.fabricmc.loader.util.version.VersionPredicateParser
import net.minecraft.command.argument.UuidArgumentType
import net.minecraft.resource.ResourceType
import net.minecraft.server.command.CommandManager
import net.minecraft.util.Identifier
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

const val MOD_ID = "vina"
const val MOD_NAME = "Vina"

@JvmField
val logger: Logger = LogManager.getLogger(MOD_NAME)

val config: VinaConfig by lazy {
    AutoConfig.register(VinaConfig::class.java, ::JanksonConfigSerializer).get()
}

object Vina : ModInitializer {
    override fun onInitialize() {
        config // lazy
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(WeightManager)
    }
}
