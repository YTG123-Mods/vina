package io.github.ytg1234.vina.mixin.polar;

import io.github.ytg1234.vina.VinaKt;
import io.github.ytg1234.vina.impl.FleeFromPolarBearGoal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.world.World;

@SuppressWarnings("ConstantConditions")
@Mixin(MobEntity.class)
public abstract class MobEntityMixin {
	@Shadow @Final private GoalSelector goalSelector;

	@Inject(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;initGoals()V"))
	private void addMeGoals(EntityType<? extends MobEntity> type, World world, CallbackInfo ci) {
		if (((Object) this instanceof AnimalEntity) && !((Object) this instanceof PolarBearEntity) && VinaKt.getConfig().getUsefulPolarBears().isFleeingEnabled()) {
			goalSelector.add(VinaKt.getConfig().getUsefulPolarBears().getGoalPriority(), new FleeFromPolarBearGoal((AnimalEntity) (Object) this, 1.0));
		}
	}
}
