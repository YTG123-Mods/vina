package io.github.ytg1234.vina.mixin;

import io.github.ytg1234.vina.Components;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
	public ItemEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Shadow public abstract ItemStack getStack();

	@Override
	public void remove() {
		if (!world.isClient) {
			Components.DROPPED_ITEMS.get(world.getLevelProperties()).addStack(getStack());
		}
		super.remove();
	}
}
