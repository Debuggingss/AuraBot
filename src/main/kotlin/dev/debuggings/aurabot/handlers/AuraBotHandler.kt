package dev.debuggings.aurabot.handlers

import dev.debuggings.aurabot.events.PacketReceivedEvent
import dev.debuggings.aurabot.utils.data.AuraBotData
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity.EnumEntityUseAction
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class AuraBotHandler : Listener {

    companion object {
        val aurabots: HashMap<Player, AuraBotData> = HashMap()
    }

    @EventHandler
    fun onPacketReceived(event: PacketReceivedEvent) {
        val packet = event.getPacket()
        val damager = event.getPlayer()

        if (packet !is PacketPlayInUseEntity) return
        if (packet.a() != EnumEntityUseAction.ATTACK) return

        val f = packet.javaClass.getDeclaredField("a")
        f.isAccessible = true
        val entityId = f.get(packet)

        val auraBotData = aurabots[damager] ?: return

        if (auraBotData.npc.id != entityId) return

        auraBotData.hits += 1

        aurabots[damager] = auraBotData
    }

    @EventHandler
    fun onLeave(event: PlayerQuitEvent) {
        val player = event.player

        val auraBotData = aurabots[player] ?: return

        auraBotData.executor.sendMessage("§cTarget left the server after hitting the bot ${auraBotData.hits} times.")
        aurabots.remove(player)
    }
}
