package io.github.ytg1234.vina.mixin.reench;

import io.github.ytg1234.vina.impl.mixin.reench.ESHImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.EnchantmentScreenHandler;

@Mixin(EnchantmentScreenHandler.class)
public abstract class EnchantmentScreenHandlerMixin {
	@Redirect(method = "generateEnchantments(Lnet/minecraft/item/ItemStack;II)Ljava/util/List;",
			  at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"))
	private Item allowEnchBooks(ItemStack self) {
		return ESHImpl.allowEnchBooks(self);
	}

	@Redirect(method = "method_17410(Lnet/minecraft/item/ItemStack;ILnet/minecraft/entity/player/PlayerEntity;ILnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
			  at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;addEnchantment(Lnet/minecraft/enchantment/Enchantment;I)V"))
	private void fixConflicts(ItemStack self, Enchantment toAdd, int level) {
		ESHImpl.fixConflict(self, toAdd, level);
	}
}
