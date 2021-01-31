package io.github.ytg1234.vina

import io.github.prospector.modmenu.api.ConfigScreenFactory
import io.github.prospector.modmenu.api.ModMenuApi
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig
import me.sargunvohra.mcmods.autoconfig1u.ConfigData
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

@Suppress("unused")
@Config(name = MOD_ID)
class VinaConfig : ConfigData {
    var enableEntityWeight = true
}

@Environment(EnvType.CLIENT)
object ModMenuIntegration : ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory { parent -> AutoConfig.getConfigScreen(VinaConfig::class.java, parent).get() }
    }
}
