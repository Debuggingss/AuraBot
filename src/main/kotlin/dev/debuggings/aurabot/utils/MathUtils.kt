package dev.debuggings.aurabot.utils

import dev.debuggings.aurabot.utils.data.Point
import kotlin.math.cos
import kotlin.math.sin

object MathUtils {

    fun getCircle(radius: Double, points: Int): ArrayList<Point> {
        val locations = arrayListOf<Point>()

        for (i in 0..points) {
            val angle = 2 * Math.PI * i / points
            val point = Point(
                radius * sin(angle),
                0.0,
                radius * cos(angle)
            )
            locations.add(point)
        }

        return locations
    }
}
