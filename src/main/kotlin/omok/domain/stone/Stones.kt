package omok.domain.stone

import omok.domain.Point
import omok.domain.rule.OmokGameRule

abstract class Stones(
    points: Set<Point> = emptySet(),
) {
    private val _points = points.toMutableSet()
    val points = _points.toSet()

    abstract val rule: OmokGameRule

    abstract operator fun plus(point: Point): Stones

    fun contains(point: Point): Boolean = point in _points

    fun lastStonePoint(): Point = _points.last()

    fun isOmok(lastPoint: Point): Boolean = rule.isOmok(_points, lastPoint)
}
