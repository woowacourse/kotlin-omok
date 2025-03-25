package omok.domain.stone

import omok.domain.Point
import omok.domain.rule.OmokRule

abstract class Stones(
    points: Set<Point> = emptySet(),
    private val omokRule: OmokRule = OmokRule(),
) {
    private val _points = points.toMutableSet()
    val points = _points.toSet()
    val lastStonePoint get(): Point = _points.last()

    abstract operator fun plus(point: Point): Stones

    fun contains(point: Point): Boolean = point in _points

    fun isOmok(lastPoint: Point): Boolean {
        return omokRule.isOmok(lastPoint, this.points)
    }
}
