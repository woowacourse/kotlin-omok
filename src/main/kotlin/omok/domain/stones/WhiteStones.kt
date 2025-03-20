package omok.domain.stones

import rule.wrapper.point.Point

class WhiteStones(
    points: Set<Point> = emptySet(),
) : Stones(points) {
    override fun plus(point: Point): WhiteStones = WhiteStones(points + point)
}
