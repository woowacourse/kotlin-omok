package woowacourse.omok.domain.model.stone

import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.rule.RenjuRule

class BlackStones(
    points: Set<Point> = emptySet(),
    private val renjuRule: RenjuRule = RenjuRule(),
) : Stones(points) {
    override fun plus(point: Point): BlackStones = BlackStones(points + point)

    fun isDoubleThreeFoul(
        other: Stones,
        point: Point,
    ): Boolean {
        return renjuRule.isDoubleThree(other, point, points)
    }

    fun isDoubleFourFoul(
        other: Stones,
        point: Point,
    ): Boolean {
        return renjuRule.isDoubleFour(other, point, points)
    }

    fun isOverLine(point: Point): Boolean {
        return renjuRule.isOverLine(point, points)
    }
}
