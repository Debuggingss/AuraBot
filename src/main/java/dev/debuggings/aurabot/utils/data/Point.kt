package dev.debuggings.aurabot.utils.data

data class Point(
    val x: Double,
    val y: Double,
    val z: Double
) {
    companion object {
        val ZERO = Point(0.0, 0.0, 0.0)
    }
}
