package io.github.ytg1234.vina

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig
import me.sargunvohra.mcmods.autoconfig1u.ConfigData
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry.Gui.CollapsibleObject
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry.Gui.EnumHandler
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry.Gui.Tooltip
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

@Suppress("unused")
@Config(name = MOD_ID)
class VinaConfig : ConfigData {
    @CollapsibleObject
    var entityWeight = EntityWeightConfig()

    @CollapsibleObject
    var godCats = GodCatsConfig()

    @CollapsibleObject
    var usefulPolarBears = UsefulPolarBearsConfig()

    @CollapsibleObject
    var reenchConfig = ReenchConfig()

    var isGildedLootTableEnabled = true

    class GodCatsConfig {
        var isEnabled = true

        @Comment("The chance value of a cat returning a lost item. If set to 1, it happens every time, if set to 2, it happens half the times, etc.")
        @Tooltip(count = 3)
        var chanceToReturnLostItem = 5

        @Comment("The bias value of prioritizing hotbar items. If it is set to 100, then 1 in 100 non-hotbar items will be ignored when choosing what to gift.")
        @Tooltip(count = 3)
        var hotbarBias = 50
    }

    class EntityWeightConfig {
        var isEnabled = true

        @Comment("Consider containers when weighing, i.e. a shulker box's weight will be calculated the same way as with hoppers.")
        @Tooltip(count = 2)
        var isInventoryAddedToItemWeight = true

        @Comment("If an entity is living, increase its weight by the slowness amplifier it has.")
        @Tooltip(count = 2)
        var isSlownessAddedToEntityWeight = true

        @Comment("Consider item stack count, i.e. a full stack will be 15 weight and one single item will be 1 weight.")
        @Tooltip(count = 2)
        var isStackSizeAddedToItemWeight = true

        @EnumHandler(option = EnumHandler.EnumDisplayOption.BUTTON)
        var pressurePlateToReplace = PressurePlate.LIGHT

        enum class PressurePlate {
            LIGHT, HEAVY
        }
    }

    class UsefulPolarBearsConfig {
        var isFleeingEnabled = true
        var isBreedingEnabled = false

        var breedingChance = 1.0
        var isHeartShown = true
        var loveTicks = 2400

        var polarBearRange = 20.0
        var goalPriority = 1000
    }

    class ReenchConfig {
        var isEnabled = true
        var isEnchantBook = false
        var maxAllowedBookshelves = 15
        var isLevelOverrideWhenGreater = true
    }
}

@Environment(EnvType.CLIENT)
object ModMenuIntegration : ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory { parent -> AutoConfig.getConfigScreen(VinaConfig::class.java, parent).get() }
    }
}
