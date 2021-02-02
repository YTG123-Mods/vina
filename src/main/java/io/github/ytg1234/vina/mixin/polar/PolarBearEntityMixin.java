package io.github.ytg1234.vina.mixin.polar;

import io.github.ytg1234.vina.impl.mixin.PBImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

@Mixin(PolarBearEntity.class)
public abstract class PolarBearEntityMixin extends AnimalEntity {
	protected PolarBearEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "tick()V", at = @At("RETURN"))
	private void tick(CallbackInfo ci) {
		PBImpl.tick(this, world, getLoveTicks(), getX(), getY(), getZ(), getBoundingBox(), this::canTarget, this::setTarget);
	}

	@Inject(method = "initGoals()V",
			at = @At(value = "NEW", args = "class=net/minecraft/entity/passive/PolarBearEntity$PolarBearEscapeDangerGoal", shift = At.Shift.AFTER))
	private void addGoal(CallbackInfo ci) {
		PBImpl.initGoals(this, goalSelector);
	}

	@Override
	public void onKilledOther(ServerWorld serverWorld, LivingEntity livingEntity) {
		super.onKilledOther(serverWorld, livingEntity);
		PBImpl.onKilledOther(this, serverWorld, livingEntity, breedingAge, canEat(), this::setLoveTicks);
	}
}
