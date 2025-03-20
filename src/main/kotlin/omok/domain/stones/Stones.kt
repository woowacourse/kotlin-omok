package omok.domain.stones

import rule.wrapper.point.Point

abstract class Stones(
    points: Set<Point> = emptySet(),
) {
    private val _points = points.toMutableSet()
    val points = _points.toSet()

    abstract operator fun plus(point: Point): Stones

    fun contains(point: Point): Boolean = point in _points

    fun lastStonePoint(): Point = _points.last()
}
