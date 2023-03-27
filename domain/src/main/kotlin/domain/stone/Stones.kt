package domain.stone

import domain.Direction
import domain.Inclination
import kotlin.math.abs

class Stones {

    private val _stones: MutableSet<Stone> = mutableSetOf()
    val stones: Set<Stone>
        get() = _stones.toSet()
    val lastPoint: Point?
        get() = if (_stones.isEmpty()) null else _stones.last().point

    fun add(stone: Stone) {
        _stones.add(stone)
    }

    operator fun contains(stone: Stone): Boolean = stone in _stones

    fun getNextBlankPointAt(point: Point, direction: Direction): Point {
        var nextPoint = point
        while (Stone(nextPoint) in this) {
            nextPoint = nextPoint goTo direction
        }
        return nextPoint
    }

    fun getLinkedStonesCountAt(point: Point, inclination: Inclination): Int {
        val onePoint = getNextBlankPointAt(point, inclination.oneDirection)
        val otherPoint = getNextBlankPointAt(point, inclination.otherDirection)

        return Integer.max(abs(onePoint.x - otherPoint.x), abs(onePoint.y - otherPoint.y)) - 1
    }

    fun completeOmok(): Boolean =
        _stones.any { stone ->
            Inclination.values().any { getLinkedStonesCountAt(stone.point, it) == 5 }
        }

    fun copy(): Stones {
        val copyStones = Stones()
        _stones.forEach { copyStones.add(it) }
        return copyStones
    }
}