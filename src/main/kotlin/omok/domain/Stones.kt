package omok.domain

import rule.wrapper.point.Point

class Stones(
    points: Set<Point> = emptySet(),
    private val color: StoneColor,
) {
    private val _points = points.toMutableSet()
    val points = _points.toSet()

    operator fun plus(point: Point): Stones = Stones(_points + point, color)

    fun contains(point: Point): Boolean = point in _points

    fun lastStonePoint(): Point = _points.last()
}
