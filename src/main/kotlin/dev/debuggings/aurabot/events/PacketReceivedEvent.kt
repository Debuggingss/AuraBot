package dev.debuggings.aurabot.events

import net.minecraft.server.v1_8_R3.Packet
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class PacketReceivedEvent(private val packet: Packet<*>, private val player: Player) : Event() {

    override fun getHandlers(): HandlerList {
        return HANDLERS
    }

    companion object {
        private val HANDLERS = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return HANDLERS
        }
    }

    fun getPacket(): Packet<*> {
        return packet
    }

    fun getPlayer(): Player {
        return player
    }
}
