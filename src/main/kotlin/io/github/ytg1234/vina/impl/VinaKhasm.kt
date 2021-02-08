package io.github.ytg1234.vina.impl

import codes.som.anthony.koffee.insns.jvm.aload_0
import codes.som.anthony.koffee.insns.jvm.aload_1
import codes.som.anthony.koffee.insns.jvm.aload_2
import codes.som.anthony.koffee.insns.jvm.aload_3
import codes.som.anthony.koffee.insns.jvm.areturn
import codes.som.anthony.koffee.insns.jvm.astore_3
import codes.som.anthony.koffee.insns.jvm.goto
import codes.som.anthony.koffee.insns.jvm.ifnonnull
import codes.som.anthony.koffee.insns.jvm.invokespecial
import codes.som.anthony.koffee.insns.jvm.invokestatic
import net.khasm.KhasmLoad
import net.khasm.transform.`class`.KhasmClassTransformerDispatcher
import net.khasm.util.mapMethod
import net.minecraft.entity.mob.AmbientEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand

object VinaKhasm : KhasmLoad {
    private const val batEntity = "net.minecraft.class_1420"
    private const val interactMobName = "method_5992"
    private const val interactMobDesc =
        "(Lnet/minecraft/class_1657;Lnet/minecraft/class_1268;)Lnet/minecraft/class_1269;"

    override fun loadTransformers() {
        KhasmClassTransformerDispatcher.registerClassTransformer {
            classTarget(batEntity)

            action {
                val interactMob = mapMethod(
                    "net.minecraft.class_1308",
                    interactMobName,
                    interactMobDesc
                )

                (method(
                    public,
                    interactMob,
                    ActionResult::class,
                    PlayerEntity::class,
                    Hand::class
                ) {
                    +L["L0"] // head of method

                    aload_0 // this
                    aload_1 // player, this
                    aload_2 // hand, player, this

                    invokestatic(
                        "io/github/ytg1234/vina/impl/mixin/BEImpl",
                        "interactMob",
                        ActionResult::class,
                        AmbientEntity::class,
                        PlayerEntity::class,
                        Hand::class
                    ) // ActionResult

                    astore_3 // ...
                    aload_3 // ActionResult
                    ifnonnull(L["L2"])

                    aload_0 // this
                    aload_1 // player, this
                    aload_2 // hand, player, this
                    invokespecial(
                        AmbientEntity::class,
                        interactMob,
                        ActionResult::class,
                        PlayerEntity::class,
                        Hand::class
                    ) // ActionResult
                    goto(L["L3"])

                    +L["L2"]
                    aload_3
                    +L["L3"]
                    areturn // ...
                }).visitAnnotation("Ljava/lang/Override;", true)
            }
        }
    }
}
