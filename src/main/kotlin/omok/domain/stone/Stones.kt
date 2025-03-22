package omok.domain.stone

import omok.domain.Point
import omok.domain.rule.OmokRule

class Stones(
    private val rule: OmokRule,
    points: Set<Point> = emptySet(),
) {
    private val _points = points.toMutableSet()
    val points = _points.toSet()

    operator fun plus(point: Point): Stones = Stones(rule, _points + point)

    fun contains(point: Point): Boolean = point in _points

    fun lastStonePoint(): Point? = _points.lastOrNull()

    fun isOmok(lastPoint: Point): Boolean = rule.isOmok(_points, lastPoint)

    fun isFoul(
        other: Stones,
        point: Point,
    ): Boolean = rule.isFoul(this.points, other.points, point)
}
