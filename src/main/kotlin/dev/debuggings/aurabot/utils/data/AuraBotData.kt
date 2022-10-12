package dev.debuggings.aurabot.utils.data

import net.minecraft.server.v1_8_R3.EntityPlayer
import org.bukkit.entity.Player

data class AuraBotData(
    val npc: EntityPlayer,
    val target: Player,
    val executor: Player,
    var hits: Int
)
