package io.github.ytg1234.vina.impl

import net.fabricmc.loader.api.FabricLoader
import org.objectweb.asm.tree.ClassNode
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin
import org.spongepowered.asm.mixin.extensibility.IMixinInfo

internal class Plugin : IMixinConfigPlugin {
    override fun onLoad(mixinPackage: String) = Unit

    override fun getRefMapperConfig() = null

    override fun shouldApplyMixin(targetClassName: String, mixinClassName: String): Boolean {
        val splt = mixinClassName.split(".")
        return if (splt[splt.size - 2] == "reench") !FabricLoader.getInstance().isModLoaded("limitless");
        else true
    }

    override fun acceptTargets(myTargets: MutableSet<String>, otherTargets: MutableSet<String>) = Unit

    override fun getMixins() = null

    override fun preApply(
        targetClassName: String,
        targetClass: ClassNode,
        mixinClassName: String,
        mixinInfo: IMixinInfo
    ) = Unit

    override fun postApply(
        targetClassName: String,
        targetClass: ClassNode,
        mixinClassName: String,
        mixinInfo: IMixinInfo
    )  = Unit
}
