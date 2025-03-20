package omok.domain.stone

import rule.OmokRule
import rule.wrapper.point.Point

abstract class Stones(
    points: Set<Point> = emptySet(),
) {
    private val _points = points.toMutableSet()
    val points = _points.toSet()

    abstract val rule: OmokRule

    abstract operator fun plus(point: Point): Stones

    fun contains(point: Point): Boolean = point in _points

    fun lastStonePoint(): Point = _points.last()

    fun isOmok(
        other: Stones,
        point: Point,
    ): Boolean = rule.checkWin(this.points.toList(), other.points.toList(), point)
}
