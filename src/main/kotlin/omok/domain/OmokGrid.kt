package omok.domain

import omok.domain.point.OmokPoint

class OmokGrid() {
    val whiteStones: Stones = Stones()
    val blackStones: Stones = Stones()

    fun validateEmptyPoint(point: OmokPoint) {
        val searchedPoint = (whiteStones.stones + blackStones.stones).find { it == point }
        check(searchedPoint == null) { ERROR_STONE_ALREADY_PUT }
    }

    fun putStone(
        point: OmokPoint,
        state: StoneState,
    ) {
        when (state) {
            StoneState.WHITE -> whiteStones + point
            StoneState.BLACK -> blackStones + point
            StoneState.BLANK -> throw IllegalStateException()
        }
    }

    fun isFull(): Boolean {
        return (whiteStones.stones + blackStones.stones).size == TOTAL_POINT_COUNT
    }

    companion object {
        const val DEFAULT_SIZE: Int = 15
        const val TOTAL_POINT_COUNT: Int = 225
        private const val ERROR_STONE_ALREADY_PUT = "이미 돌이 있습니다."
    }
}
