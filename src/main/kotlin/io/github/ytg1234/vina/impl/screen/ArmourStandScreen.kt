package io.github.ytg1234.vina.impl.screen

import io.github.cottonmc.cotton.gui.client.CottonClientScreen
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription
import io.github.cottonmc.cotton.gui.widget.WButton
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.WSprite
import net.minecraft.text.LiteralText
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier

class ArmourStandScreen : CottonClientScreen(TranslatableText("text.vina.screen.as.title"), ArmourStandDescription())

class ArmourStandDescription : LightweightGuiDescription() {
    init {
        val root = WGridPanel(45)
        setRootPanel(root)
        root.setSize(5, 10)

        val icon = WSprite(Identifier("minecraft", "textures/item/armor_stand.png"))
        root.add(icon, 0, 2, 1, 1)

        val button1 = WButton(TranslatableText("text.vina.screen.as.button.1.title"))
        root.add(button1, 0, 3, 4, 1)

        val button2 = WButton(LiteralText("2"))
        root.add(button2, 1, 1, 1, 1)

        root.validate(this)
    }
}
