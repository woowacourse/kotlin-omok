package woowacourse.omok.domain.model.stone

import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.rule.OmokRule

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
