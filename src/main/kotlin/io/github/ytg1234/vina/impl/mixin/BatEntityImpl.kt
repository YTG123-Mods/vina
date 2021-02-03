@file:JvmName("BEImpl")

package io.github.ytg1234.vina.impl.mixin

import net.fabricmc.fabric.api.networking.v1.PlayerLookup
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.passive.BatEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Items
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ChunkTicketType
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.world.Heightmap
import java.util.EnumSet

fun BatEntity.interactMob(player: PlayerEntity, hand: Hand): ActionResult? {
    if (player.getStackInHand(hand).item === Items.SPIDER_EYE) {
        if (!world.isClient) {
            val serverPlayer = player as ServerPlayerEntity
            for (playerEntity in PlayerLookup.tracking(this)) {
                world.playSoundFromEntity(
                    playerEntity,
                    this,
                    SoundEvents.ENTITY_WOLF_PANT,
                    SoundCategory.AMBIENT,
                    1f,
                    1f
                )
            }
            val bp = serverPlayer.blockPos
            val teleBp = BlockPos(bp.x, world.getTopY(Heightmap.Type.WORLD_SURFACE, bp.x, bp.z) + 1, bp.z)
            (world as ServerWorld).chunkManager.addTicket(
                ChunkTicketType.POST_TELEPORT,
                ChunkPos(teleBp),
                1,
                serverPlayer.entityId
            )
            serverPlayer.networkHandler.teleportRequest(
                teleBp.x.toDouble(),
                teleBp.y.toDouble(),
                teleBp.z.toDouble(),
                serverPlayer.yaw,
                serverPlayer.pitch,
                EnumSet.noneOf(PlayerPositionLookS2CPacket.Flag::class.java)
            )
            serverPlayer.getStackInHand(hand).decrement(1)

            if (serverPlayer.isCreative || !isInvulnerable) {
                damage(DamageSource.player(serverPlayer), Float.MAX_VALUE)
            }
        }
        return ActionResult.success(world.isClient)
    }
    return null
}
