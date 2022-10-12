package dev.debuggings.aurabot.handlers

import dev.debuggings.aurabot.events.PacketReceivedEvent
import dev.debuggings.aurabot.events.PacketSentEvent
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import net.minecraft.server.v1_8_R3.Packet
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PacketHandler : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        injectPlayer(event.player)
    }

    private fun injectPlayer(player: Player) {
        val channelDuplexHandler = object : ChannelDuplexHandler() {

            @Throws(Exception::class)
            override fun channelRead(
                channelHandlerContext: ChannelHandlerContext,
                packet: Any
            ) {
                val event = PacketReceivedEvent(packet as Packet<*>, player)
                Bukkit.getPluginManager().callEvent(event)

                super.channelRead(channelHandlerContext, packet)
            }

            @Throws(Exception::class)
            override fun write(
                channelHandlerContext: ChannelHandlerContext,
                packet: Any,
                channelPromise: ChannelPromise
            ) {
                val event = PacketSentEvent(packet as Packet<*>, player)
                Bukkit.getPluginManager().callEvent(event)

                super.write(channelHandlerContext, packet, channelPromise)
            }
        }

        val pipeline = (player as CraftPlayer).handle.playerConnection.networkManager.channel.pipeline()
        pipeline.addBefore("packet_handler", "packet_handler_aurabot", channelDuplexHandler as ChannelHandler)
    }
}
