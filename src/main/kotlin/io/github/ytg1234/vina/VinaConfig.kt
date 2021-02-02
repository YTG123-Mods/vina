package io.github.ytg1234.vina

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig
import me.sargunvohra.mcmods.autoconfig1u.ConfigData
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry.Gui.CollapsibleObject
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry.Gui.EnumHandler
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

@Suppress("unused")
@Config(name = MOD_ID)
class VinaConfig : ConfigData {
    @CollapsibleObject
    var entityWeightConfig = EntityWeightConfig()

    @CollapsibleObject
    var godCatsConfig = GodCatsConfig()

    class GodCatsConfig {
        var isEnabled = true

        @field:Comment("The chance value of a cat returning a lost item. If set to 1, it happens every time, if set to 2, it happens half the times, etc.")
        var chanceToReturnLostItem = 5

        @field:Comment("The bias value of prioritizing hotbar items. If it is set to 100, then 1 in 100 non-hotbar items will be ignored when choosing what to gift.")
        var hotbarBias = 50
    }

    class EntityWeightConfig {
        var isEnabled = true
        var isInventoryAddedToItemWeight = true
        var isSlownessAddedToEntityWeight = true

        @EnumHandler(option = EnumHandler.EnumDisplayOption.BUTTON)
        var pressurePlateToReplace = PressurePlate.LIGHT

        enum class PressurePlate {
            LIGHT, HEAVY
        }
    }
}

@Environment(EnvType.CLIENT)
object ModMenuIntegration : ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory { parent -> AutoConfig.getConfigScreen(VinaConfig::class.java, parent).get() }
    }
}
