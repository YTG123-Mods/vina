package io.github.ytg1234.vina.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.entity.player.PlayerInventory;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {
	@Inject(method = "dropAll()V",
			at = @At(value = "INVOKE",
					 target = "Lnet/minecraft/entity/player/PlayerEntity;dropItem(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/ItemEntity;"), locals = LocalCapture.PRINT)
	private void addTag(CallbackInfo ci) {

	}
}
