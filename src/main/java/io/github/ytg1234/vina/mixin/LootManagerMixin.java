package io.github.ytg1234.vina.mixin;

import java.util.Map;

import com.google.gson.JsonElement;
import io.github.ytg1234.vina.impl.mixin.LMImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.loot.LootManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

@Mixin(LootManager.class)
public abstract class LootManagerMixin {
	@Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V", at = @At("HEAD"))
	private void addGilded(Map<Identifier, JsonElement> map, ResourceManager manager, Profiler profiler, CallbackInfo ci) {
		LMImpl.addGilded(map);
	}
}
