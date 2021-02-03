package io.github.ytg1234.vina.mixin.bats;

import io.github.ytg1234.vina.impl.mixin.BEImpl;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@SuppressWarnings("ConstantConditions")
@Mixin(BatEntity.class)
public abstract class BatEntityMixin extends AmbientEntity {
	protected BatEntityMixin(EntityType<? extends AmbientEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected ActionResult interactMob(PlayerEntity player, Hand hand) {
		ActionResult result = BEImpl.interactMob((BatEntity) (Object) this, player, hand);
		return result == null ? super.interactMob(player, hand) : result;
	}
}
