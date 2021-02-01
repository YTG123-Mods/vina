package io.github.ytg1234.vina.mixin;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import io.github.ytg1234.vina.Components;
import io.github.ytg1234.vina.VinaKt;
import io.github.ytg1234.vina.api.DroppedItem;
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
		if (!self.isClient && VinaKt.getConfig().getGodCatsConfig().getEnabled()) {
			Random random = new Random(); // Not interrupt the previous one, for RNG manipulation needs

			boolean bl = VinaKt.getConfig().getGodCatsConfig().getChanceToReturnLostItem() <= 1 ||
						 random.nextInt(VinaKt.getConfig().getGodCatsConfig().getChanceToReturnLostItem() - 1) < 1;

			if (bl) {
				List<ItemStack> stacks = Components.DROPPED_ITEMS.get(self.getLevelProperties()).getDroppedItems().stream().filter(stack -> {
					if (!DroppedItem.fromStack(stack).isHotbar()) {
						if (VinaKt.getConfig().getGodCatsConfig().getHotbarBias() <= 1) return false;
						return random.nextInt(VinaKt.getConfig().getGodCatsConfig().getHotbarBias() - 1) > 0;
					}
					return true;
				}).collect(Collectors.toList());

				ItemStack chosen = stacks.get(random.nextInt(stacks.size()));
				Components.DROPPED_ITEMS.get(self.getLevelProperties()).removeStack(chosen);
				((ItemEntity) ett).setStack(chosen);
			}
		}
		return self.spawnEntity(ett);
	}
}
