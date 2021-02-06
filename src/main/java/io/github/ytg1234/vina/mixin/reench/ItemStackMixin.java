package io.github.ytg1234.vina.mixin.reench;

import io.github.ytg1234.vina.VinaKt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.item.ItemStack;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	@Redirect(method = "isEnchantable", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;hasEnchantments()Z"))
	private boolean allowReench(ItemStack self) {
		return !VinaKt.getConfig().getReenchConfig().isEnabled() && self.hasEnchantments();
	}
}
