package woowacourse.omok.domain.grid

import woowacourse.omok.domain.StoneColor

class OmokGrid {
    private val stones: Stones = Stones()

    fun putStone(stone: Stone) {
        stones + stone
    }

    fun isFull(): Boolean {
        return stones.isSizeEqualTo(TOTAL_POINT_COUNT)
    }

    fun isBlackMoreThanWhite(): Boolean {
        return stones.hasMoreStonesThan(StoneColor.BLACK, StoneColor.WHITE)
    }

    fun getStoneColorByPoint(point: Point): StoneColor? {
        return stones.getStoneByPoint(point)?.stoneColor
    }

    fun getStonesByColor(stoneColor: StoneColor): Set<Stone> {
        return stones.getStonesByColor(stoneColor)
    }

    companion object {
        const val MIN_BOUND: Int = 1
        const val DEFAULT_SIZE: Int = 15
        private const val TOTAL_POINT_COUNT: Int = 225
    }
}
