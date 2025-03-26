package woowacourse.omok.domain.grid

import woowacourse.omok.domain.StoneColor

class OmokGrid {
    private val stones: Stones = Stones()

    fun putStone(point: OmokPoint) {
        stones + point
    }

    fun isFull(): Boolean {
        return (stones.stones).size == TOTAL_POINT_COUNT
    }

    fun getStonesByColor(stoneColor: StoneColor): Set<OmokPoint> {
        return stones.stones.filter { it.stoneColor == stoneColor }.toSet()
    }

    fun getStoneByPoint(point: Point): OmokPoint? {
        return stones.stones.find { it.point == point }
    }

    companion object {
        const val MIN_BOUND: Int = 1
        const val DEFAULT_SIZE: Int = 15
        private const val TOTAL_POINT_COUNT: Int = 225
    }
}
