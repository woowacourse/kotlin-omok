package woowacourse.omok.domain.grid

import woowacourse.omok.domain.StoneColor

class OmokGrid {
    private val stones: Stones = Stones()

    fun putStone(point: Stone) {
        stones + point
    }

    fun isFull(): Boolean {
        return (stones.stones).size == TOTAL_POINT_COUNT
    }

    fun getStonesByColor(stoneColor: StoneColor): Set<Stone> {
        return stones.stones.filter { it.stoneColor == stoneColor }.toSet()
    }

    fun isBlackMoreThanWhite(): Boolean {
        val blackStones = getStonesByColor(StoneColor.BLACK)
        val whiteStones = getStonesByColor(StoneColor.WHITE)
        return blackStones.size > whiteStones.size
    }

    fun getStoneColorByPoint(point: Point): StoneColor? {
        return stones.stones.find { it.point == point }?.stoneColor
    }

    companion object {
        const val MIN_BOUND: Int = 1
        const val DEFAULT_SIZE: Int = 15
        private const val TOTAL_POINT_COUNT: Int = 225
    }
}
