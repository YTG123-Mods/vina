package io.github.ytg1234.vina.mixin;

import java.util.List;
import java.util.Random;

import io.github.ytg1234.vina.Components;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(targets = "net/minecraft/entity/passive/CatEntity$SleepWithOwnerGoal")
public abstract class CatEntity$SleepWithOwnerGoalMixin extends Goal {
	@Redirect(method = "dropMorningGifts()V",
			  at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
	private boolean lostItems(World self, Entity ett) {
		if (!self.isClient) {
			Random random = new Random(); // Not interrupt the previous one, for RNG manipulation needs

			List<ItemStack> stacks = Components.DROPPED_ITEMS.get(self.getLevelProperties()).getDroppedItems();
			ItemStack chosen = stacks.get(random.nextInt(stacks.size()));
			Components.DROPPED_ITEMS.get(self.getLevelProperties()).removeStack(chosen);
			((ItemEntity) ett).setStack(chosen);
		}
		return self.spawnEntity(ett);
	}
}
