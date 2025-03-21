package omok.domain

import omok.domain.point.OmokPoint

class OmokGrid() {
    val whiteStones: Stones = Stones()
    val blackStones: Stones = Stones()

    fun putStone(
        point: OmokPoint,
        state: StoneState,
    ) {
        when (state) {
            StoneState.WHITE -> whiteStones + point
            StoneState.BLACK -> blackStones + point
        }
    }

    fun getTotalStones(): Set<OmokPoint> {
        return whiteStones.stones + blackStones.stones
    }

    fun isFull(): Boolean {
        return getTotalStones().size == TOTAL_POINT_COUNT
    }

    companion object {
        const val DEFAULT_SIZE: Int = 15
        const val TOTAL_POINT_COUNT: Int = 225
    }
}
