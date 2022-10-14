package dev.debuggings.aurabot.commands

import dev.debuggings.aurabot.AuraBot
import dev.debuggings.aurabot.handlers.AuraBotHandler
import dev.debuggings.aurabot.utils.MathUtils
import dev.debuggings.aurabot.utils.NPCUtils
import dev.debuggings.aurabot.utils.Utils
import dev.debuggings.aurabot.utils.data.AuraBotData
import dev.debuggings.aurabot.utils.data.Point
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import kotlin.random.Random

class AuraBotCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§cYou must be a player to execute this command!")
            return true
        }

        if (!sender.hasPermission("aurabot.aurabot")) {
            sender.sendMessage("§cYou do not have permission to execute this command!")
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage("§cInvalid Syntax! Correct: /aurabot <name> [attack]")
            return true
        }

        val target = Bukkit.getPlayerExact(args[0])

        if (target == null) {
            sender.sendMessage("§cCan't find player '${args[0]}'!")
            return true
        }

        if (AuraBotHandler.aurabots[target] != null) {
            sender.sendMessage("§cTarget already being tested.")
            return true
        }

        val locations = MathUtils.getCircle(2.5, 20)
        val name = "§c${Utils.getRandomName()}".take(16)
        val equipment = Utils.getRandomEquipment()
        val recipients = arrayListOf(target, sender)
        val doDamage = args.size >= 2

        var index = 0
        var hitindex = 0
        var rounds = 0

        val npc = NPCUtils.createNPC(target.location.add(0.0, 10.0, 0.0), name)

        NPCUtils.sendAddPacket(npc, recipients)
        NPCUtils.sendSetArmorPacket(npc, recipients, equipment)

        AuraBotHandler.aurabots[target] = AuraBotData(
            npc,
            target,
            sender,
            0
        )

        sender.sendMessage("§aAura checking '${target.name}'...")

        object : BukkitRunnable() {

            override fun run() {
                if (AuraBotHandler.aurabots[target] == null) {
                    NPCUtils.sendRemovePacket(npc, recipients)

                    cancel()
                    return
                }

                if (index >= locations.size) {
                    if (rounds >= 3) {
                        sender.sendMessage("§aTarget hit the bot ${AuraBotHandler.aurabots[target]?.hits} times.")

                        NPCUtils.sendRemovePacket(npc, recipients)
                        AuraBotHandler.aurabots.remove(target)

                        cancel()
                        return
                    }

                    sender.sendMessage("§aRound ${rounds + 1}/3 complete...")

                    rounds++
                    index = 0
                }

                if (hitindex != 0 && hitindex % 20 == 0) {
                    NPCUtils.sendSwingHandPacket(npc, recipients)

                    if (doDamage) {
                        target.damage(0.0, npc.bukkitEntity)
                        sender.sendMessage("§bHit the player.")
                    }
                }

                val loc = locations[index]

                NPCUtils.sendMovePacket(npc, recipients, target.location.add(loc.x, loc.y + 1, loc.z))
                NPCUtils.sendRotationPacket(
                    npc,
                    recipients,
                    Random.nextDouble(-180.0, 180.0),
                    Random.nextDouble(-90.0, 90.0)
                )

                index++
                hitindex++
            }
        }.runTaskTimer(AuraBot.INSTANCE, 0, 1)

        return true
    }
}
