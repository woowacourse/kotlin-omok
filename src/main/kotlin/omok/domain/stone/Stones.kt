package omok.domain.stone

import omok.domain.Point
import omok.domain.rule.OmokRule

abstract class Stones(
    points: Set<Point> = emptySet(),
    private val omokRule: OmokRule = OmokRule(points)
) {
    private val _points = points.toMutableSet()
    val points = _points.toSet()

    abstract operator fun plus(point: Point): Stones

    fun contains(point: Point): Boolean = point in _points

    fun lastStonePoint(): Point = _points.last()

    fun isOmok(
        lastPoint: Point,
        size: Int,
    ): Boolean = omokRule.isOmok(lastPoint, size)
}
