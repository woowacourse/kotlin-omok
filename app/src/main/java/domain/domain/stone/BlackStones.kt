package domain.domain.stone

import domain.domain.Point
import domain.domain.rule.RenjuRule

class BlackStones(
    points: Set<Point> = emptySet(),
    private val renjuRule: RenjuRule = RenjuRule(),
) : Stones(points) {
    override fun plus(point: Point): BlackStones = BlackStones(points + point)

    fun isFoul(
        other: Stones,
        point: Point,
    ): Boolean {
        return renjuRule.isFoul(other, point, points)
    }
}
