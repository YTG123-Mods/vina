package io.github.ytg1234.vina.mixin.reench;

import java.util.List;
import java.util.Random;

import io.github.ytg1234.vina.impl.mixin.reench.EHImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.ItemStack;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
	@ModifyVariable(method = "generateEnchantments(Ljava/util/Random;Lnet/minecraft/item/ItemStack;IZ)Ljava/util/List;",
					at = @At(value = "INVOKE_ASSIGN",
							 target = "Lnet/minecraft/enchantment/EnchantmentHelper;getPossibleEntries(ILnet/minecraft/item/ItemStack;Z)Ljava/util/List;"),
					ordinal = 1)
	private static List<EnchantmentLevelEntry> yeetConflictsAway(List<EnchantmentLevelEntry> possibleEnchs, Random random, ItemStack stack) {
		return EHImpl.removeConflicts0(possibleEnchs, stack);
	}

	@Redirect(method = "generateEnchantments(Ljava/util/Random;Lnet/minecraft/item/ItemStack;IZ)Ljava/util/List;",
			  at = @At(value = "INVOKE",
					   target = "Lnet/minecraft/enchantment/EnchantmentHelper;removeConflicts(Ljava/util/List;Lnet/minecraft/enchantment/EnchantmentLevelEntry;)V"))
	private static void yeetConflictsAway2(List<EnchantmentLevelEntry> possibleEnchs, EnchantmentLevelEntry picked) {
		possibleEnchs = EHImpl.removeConflicts2(possibleEnchs, picked);
	}

	@ModifyConstant(method = "calculateRequiredExperienceLevel(Ljava/util/Random;IILnet/minecraft/item/ItemStack;)I",
					constant = @Constant(intValue = 15, ordinal = -1))
	private static int maxBookshelves(int previous) {
		return EHImpl.getMaxBookshelves();
	}
}
