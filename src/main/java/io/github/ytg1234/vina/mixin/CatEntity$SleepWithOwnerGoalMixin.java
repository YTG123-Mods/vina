package io.github.ytg1234.vina.mixin;

import io.github.ytg1234.vina.impl.mixin.SlpGlImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.world.World;

@Mixin(targets = "net/minecraft/entity/passive/CatEntity$SleepWithOwnerGoal")
public abstract class CatEntity$SleepWithOwnerGoalMixin extends Goal {
	@Redirect(method = "dropMorningGifts()V",
			  at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
	private boolean lostItems(World self, Entity ett) {
		return SlpGlImpl.lostItems(self, ett);
	}
}
