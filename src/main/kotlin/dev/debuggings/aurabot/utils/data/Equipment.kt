package dev.debuggings.aurabot.utils.data

import org.bukkit.inventory.ItemStack

data class Equipment(
    val heldItem: ItemStack,
    val helmet: ItemStack,
    val chestplate: ItemStack,
    val leggings: ItemStack,
    val boots: ItemStack
)
