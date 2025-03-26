package domain.domain.stone

import domain.domain.Point

class WhiteStones(
    points: Set<Point> = emptySet(),
) : Stones(points) {
    override fun plus(point: Point): WhiteStones = WhiteStones(points + point)
}
