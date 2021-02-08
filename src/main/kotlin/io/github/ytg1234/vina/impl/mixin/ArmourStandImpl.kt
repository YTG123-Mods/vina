@file:JvmName("ASImpl")

package io.github.ytg1234.vina.impl.mixin

import io.github.ytg1234.vina.impl.screen.ArmourStandScreen
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.decoration.ArmorStandEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResult
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

internal fun ArmorStandEntity.hookScreen(
    player: PlayerEntity,
    cir: CallbackInfoReturnable<ActionResult>
) {
    if (world.isClient() && player.uuid == MinecraftClient.getInstance().player!!.uuid) {
        MinecraftClient.getInstance().openScreen(ArmourStandScreen())
        cir.returnValue = ActionResult.SUCCESS
    }
}
