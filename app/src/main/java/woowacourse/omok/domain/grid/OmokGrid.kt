package woowacourse.omok.domain.grid

import woowacourse.omok.domain.StoneColor

class OmokGrid {
    private val whiteStones: Stones = Stones()
    private val blackStones: Stones = Stones()

    fun putStone(
        point: OmokPoint,
        state: StoneColor,
    ) {
        when (state) {
            StoneColor.WHITE -> whiteStones + point
            StoneColor.BLACK -> blackStones + point
        }
    }

    fun isFull(): Boolean {
        return (whiteStones.stones + blackStones.stones).size == TOTAL_POINT_COUNT
    }

    fun getStones(stoneColor: StoneColor): Set<OmokPoint> {
        return when (stoneColor) {
            StoneColor.BLACK -> blackStones.stones
            StoneColor.WHITE -> whiteStones.stones
        }
    }

    companion object {
        const val MIN_BOUND: Int = 1
        const val DEFAULT_SIZE: Int = 15
        private const val TOTAL_POINT_COUNT: Int = 225
    }
}
