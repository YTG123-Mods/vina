package io.github.ytg1234.vina.mixin.reench;

import io.github.ytg1234.vina.VinaKt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.item.EnchantedBookItem;

@Mixin(EnchantedBookItem.class)
public abstract class EnchantedBookItemMixin {
	@ModifyConstant(method = "isEnchantable(Lnet/minecraft/item/ItemStack;)Z", constant = @Constant(intValue = 0, ordinal = 0))
	private int denyWhenConfig(int original) {
		if (VinaKt.getConfig().getReenchConfig().isEnabled() && VinaKt.getConfig().getReenchConfig().isEnchantBook() || original > 0) {
			return 1;
		}
		return 0;
	}
}
