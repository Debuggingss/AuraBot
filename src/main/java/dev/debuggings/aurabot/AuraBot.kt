package dev.debuggings.aurabot

import dev.debuggings.aurabot.commands.AuraBotCommand
import dev.debuggings.aurabot.handlers.AuraBotHandler
import dev.debuggings.aurabot.handlers.PacketHandler
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class AuraBot : JavaPlugin() {

    companion object {
        lateinit var INSTANCE: JavaPlugin
    }

    override fun onEnable() {
        INSTANCE = this

        this.saveDefaultConfig()

        getCommand("aurabot").executor = AuraBotCommand()

        Bukkit.getPluginManager().registerEvents(PacketHandler(), this)
        Bukkit.getPluginManager().registerEvents(AuraBotHandler(), this)

        Bukkit.getConsoleSender().sendMessage("§8[§aAuraBot§8] §aPlugin started!")
    }
}
