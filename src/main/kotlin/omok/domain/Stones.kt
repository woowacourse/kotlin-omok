package omok.domain

import rule.wrapper.point.Point

class Stones() {
    private val _stones: MutableSet<Point> = mutableSetOf()
    val stones: Set<Point> get() = _stones.deepCopy()

    operator fun plus(point: Point) {
        _stones.add(point)
    }
}

fun MutableSet<Point>.deepCopy(): Set<Point> = map { it.copy() }.toSet()
