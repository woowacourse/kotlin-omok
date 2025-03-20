package omok.domain.stones

import rule.wrapper.point.Point

class BlackStones(
    points: Set<Point> = emptySet(),
) : Stones(points) {
    override fun plus(point: Point): BlackStones = BlackStones(points + point)
}
