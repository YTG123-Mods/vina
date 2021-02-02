package io.github.ytg1234.vina.mixin.polar;

import io.github.ytg1234.vina.VinaKt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
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
		if (!world.isClient && getLoveTicks() <= 0 && VinaKt.getConfig().getUsefulPolarBearsConfig().isBreedingEnabled()) {
			AnimalEntity closest = world.getClosestEntity(
					AnimalEntity.class,
					new TargetPredicate().setPredicate(e -> !(e instanceof PolarBearEntity)),
					this,
					getX(),
					getY(),
					getZ(),
					getBoundingBox().expand(8, 4, 0)
			);

			if (closest != null && canTarget(closest)) {
				setTarget(closest);
			}
		}
	}

	@Inject(method = "initGoals()V",
			at = @At(value = "NEW", args = "class=net/minecraft/entity/passive/PolarBearEntity$PolarBearEscapeDangerGoal", shift = At.Shift.AFTER))
	private void addGoal(CallbackInfo ci) {
		if (VinaKt.getConfig().getUsefulPolarBearsConfig().isBreedingEnabled()) {
			goalSelector.add(3, new AnimalMateGoal(this, VinaKt.getConfig().getUsefulPolarBearsConfig().getBreedingChance()));
		}
	}

	@Override
	public void onKilledOther(ServerWorld serverWorld, LivingEntity livingEntity) {
		super.onKilledOther(serverWorld, livingEntity);
		if (!world.isClient &&
			livingEntity instanceof AnimalEntity &&
			!(livingEntity instanceof PolarBearEntity) &&
			breedingAge == 0 &&
			canEat() &&
			VinaKt.getConfig().getUsefulPolarBearsConfig().isBreedingEnabled()) {
			setLoveTicks(VinaKt.getConfig().getUsefulPolarBearsConfig().getLoveTicks());
			if (VinaKt.getConfig().getUsefulPolarBearsConfig().isHeartShown()) {
				world.sendEntityStatus(this, (byte) 18);
			}
		}
	}
}
