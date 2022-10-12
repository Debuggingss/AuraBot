package dev.debuggings.aurabot.utils

import dev.debuggings.aurabot.AuraBot
import dev.debuggings.aurabot.utils.data.Equipment
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

object Utils {

    private val helmets = arrayListOf(
        Material.DIAMOND_HELMET,
        Material.GOLD_HELMET,
        Material.IRON_HELMET,
        Material.CHAINMAIL_HELMET,
        Material.LEATHER_HELMET,
        Material.AIR
    )

    private val chestplates = arrayListOf(
        Material.DIAMOND_CHESTPLATE,
        Material.GOLD_CHESTPLATE,
        Material.IRON_CHESTPLATE,
        Material.CHAINMAIL_CHESTPLATE,
        Material.LEATHER_CHESTPLATE,
        Material.AIR
    )

    private val leggings = arrayListOf(
        Material.DIAMOND_LEGGINGS,
        Material.GOLD_LEGGINGS,
        Material.IRON_LEGGINGS,
        Material.CHAINMAIL_LEGGINGS,
        Material.LEATHER_LEGGINGS,
        Material.AIR
    )

    private val boots = arrayListOf(
        Material.DIAMOND_BOOTS,
        Material.GOLD_BOOTS,
        Material.IRON_BOOTS,
        Material.CHAINMAIL_BOOTS,
        Material.LEATHER_BOOTS,
        Material.AIR
    )

    private val weapons = arrayListOf(
        Material.DIAMOND_SWORD,
        Material.GOLD_SWORD,
        Material.IRON_SWORD,
        Material.STONE_SWORD,
        Material.WOOD_SWORD,
        Material.DIAMOND_AXE,
        Material.GOLD_AXE,
        Material.IRON_AXE,
        Material.STONE_AXE,
        Material.WOOD_AXE,
        Material.AIR
    )

    private val words: MutableList<String> = AuraBot.INSTANCE.config.getStringList("name_words")
    
    fun getRandomName(): String {
        return (words.random() + words.random() + Random.nextInt(1000, 2000))
    }

    fun getRandomEquipment(): Equipment {
        return Equipment(
            ItemStack(weapons.random()),
            ItemStack(helmets.random()),
            ItemStack(chestplates.random()),
            ItemStack(leggings.random()),
            ItemStack(boots.random())
        )
    }
}
