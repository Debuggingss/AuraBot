package dev.debuggings.aurabot.utils

import com.mojang.authlib.GameProfile
import dev.debuggings.aurabot.AuraBot
import dev.debuggings.aurabot.utils.data.Equipment
import net.minecraft.server.v1_8_R3.*
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity.PacketPlayOutEntityLook
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_8_R3.CraftServer
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

object NPCUtils {

    fun createNPC(location: Location, name: String): EntityPlayer {
        val nmsServer = (Bukkit.getServer() as CraftServer).server
        val nmsWorld = (location.world as CraftWorld).handle
        val gameProfile = GameProfile(UUID.randomUUID(), name)

        val npc = EntityPlayer(
            nmsServer,
            nmsWorld,
            gameProfile,
            PlayerInteractManager(nmsWorld)
        )

        npc.setLocation(
            location.x,
            location.y,
            location.z,
            location.yaw,
            location.pitch
        )

        return npc
    }

    fun sendAddPacket(npc: EntityPlayer, players: ArrayList<Player>) {
        players.forEach {
            sendAddPacket(npc, it)
        }
    }

    fun sendAddPacket(npc: EntityPlayer, player: Player) {
        val connection = (player as CraftPlayer).handle.playerConnection

        connection.sendPacket(PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc))
        connection.sendPacket(PacketPlayOutNamedEntitySpawn(npc))

        object : BukkitRunnable() {
            override fun run() {
                connection.sendPacket(
                    PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc)
                )
            }
        }.runTaskLater(AuraBot.INSTANCE, 20)

        connection.sendPacket(
            PacketPlayOutEntityHeadRotation(
                npc,
                (npc.yaw * 256 / 360).toInt().toByte()
            )
        )
    }

    fun sendRemovePacket(npc: EntityPlayer, players: ArrayList<Player>) {
        players.forEach {
            sendRemovePacket(npc, it)
        }
    }

    fun sendRemovePacket(npc: EntityPlayer, player: Player) {
        (player as CraftPlayer).handle.playerConnection.sendPacket(PacketPlayOutEntityDestroy(npc.id))
    }

    fun sendMovePacket(npc: EntityPlayer, players: ArrayList<Player>, location: Location) {
        players.forEach {
            sendMovePacket(npc, it, location)
        }
    }

    fun sendMovePacket(npc: EntityPlayer, player: Player, location: Location) {
        (player as CraftPlayer).handle.playerConnection.sendPacket(PacketPlayOutEntityTeleport(
            npc.id,
            (location.x * 32).toInt(),
            (location.y * 32).toInt(),
            (location.z * 32).toInt(),
            0.toByte(),
            0.toByte(),
            false
        ))
    }

    fun sendRotationPacket(npc: EntityPlayer, players: ArrayList<Player>, yaw: Double, pitch: Double) {
        players.forEach {
            sendRotationPacket(npc, it, yaw, pitch)
        }
    }

    fun sendRotationPacket(npc: EntityPlayer, player: Player, yaw: Double, pitch: Double) {
        (player as CraftPlayer).handle.playerConnection.sendPacket(PacketPlayOutEntityLook(
            npc.id,
            yaw.toInt().toByte(),
            pitch.toInt().toByte(),
            false
        ))
    }

    fun sendSetArmorPacket(npc: EntityPlayer, players: ArrayList<Player>, equipment: Equipment) {
        players.forEach {
            sendSetArmorPacket(npc, it, equipment)
        }
    }

    fun sendSetArmorPacket(npc: Entity, player: Player, equipment: Equipment) {
        val connection = (player as CraftPlayer).handle.playerConnection

        connection.sendPacket(PacketPlayOutEntityEquipment(
            npc.id,
            0,
            CraftItemStack.asNMSCopy(equipment.heldItem)
        ))

        connection.sendPacket(PacketPlayOutEntityEquipment(
            npc.id,
            1,
            CraftItemStack.asNMSCopy(equipment.helmet)
        ))

        connection.sendPacket(PacketPlayOutEntityEquipment(
            npc.id,
            2,
            CraftItemStack.asNMSCopy(equipment.chestplate)
        ))

        connection.sendPacket(PacketPlayOutEntityEquipment(
            npc.id,
            3,
            CraftItemStack.asNMSCopy(equipment.leggings)
        ))

        connection.sendPacket(PacketPlayOutEntityEquipment(
            npc.id,
            4,
            CraftItemStack.asNMSCopy(equipment.boots)
        ))
    }

    fun sendSwingHandPacket(npc: EntityPlayer, players: ArrayList<Player>) {
        players.forEach {
            sendSwingHandPacket(npc, it)
        }
    }

    fun sendSwingHandPacket(npc: Entity, player: Player) {
        (player as CraftPlayer).handle.playerConnection.sendPacket(PacketPlayOutAnimation(
            npc,
            0
        ))
    }
}
