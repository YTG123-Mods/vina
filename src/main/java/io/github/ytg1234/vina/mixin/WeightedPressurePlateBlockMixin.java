package io.github.ytg1234.vina.mixin;

import io.github.ytg1234.vina.VinaConfig;
import io.github.ytg1234.vina.impl.mixin.WprImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import static io.github.ytg1234.vina.VinaKt.*;

import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.WeightedPressurePlateBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(WeightedPressurePlateBlock.class)
public abstract class WeightedPressurePlateBlockMixin extends AbstractPressurePlateBlock {
	protected WeightedPressurePlateBlockMixin(Settings settings) {
		super(settings);
	}

	@Inject(method = "getRedstoneOutput(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)I", at = @At("HEAD"), cancellable = true)
	private void weightStuff(World world, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
		if (getConfig().getEntityWeightConfig().isEnabled() && this == (
				getConfig().getEntityWeightConfig().getPressurePlateToReplace() == VinaConfig.EntityWeightConfig.PressurePlate.HEAVY ?
				Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE :
				Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE
		)) {
			cir.setReturnValue(WprImpl.weigh(world, pos, BOX));
		}
	}
}
