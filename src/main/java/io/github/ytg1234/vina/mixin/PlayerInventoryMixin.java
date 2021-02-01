package io.github.ytg1234.vina.mixin;

import java.util.Iterator;
import java.util.List;

import io.github.ytg1234.vina.api.DroppedItem;
import io.github.ytg1234.vina.impl.api.ItemStackDroppedItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {
	@Shadow
	@Final
	public PlayerEntity player;

	@Inject(method = "dropAll()V",
			at = @At(value = "INVOKE",
					 target = "Lnet/minecraft/entity/player/PlayerEntity;dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;"),
			locals = LocalCapture.CAPTURE_FAILHARD)
	private void addTag(CallbackInfo ci, Iterator<DefaultedList<ItemStack>> iter, List<ItemStack> list, int i, ItemStack stack) {
		if (!player.world.isClient) {
			DroppedItem item = new ItemStackDroppedItem(player.getUuid(), (i > 0 && i < 9));
			stack.setTag(item.writeToNbt(stack.getOrCreateTag()));
		}
	}
}
