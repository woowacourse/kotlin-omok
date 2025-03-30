package woowacourse.omok.domain.model.stone

import woowacourse.omok.domain.model.Point

class WhiteStones(
    points: Set<Point> = emptySet(),
) : Stones(points) {
    override fun plus(point: Point): WhiteStones = WhiteStones(points + point)
}
