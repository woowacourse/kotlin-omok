package omok.domain

import omok.domain.point.OmokPoint

class OmokGrid() {
    val whiteStones: Stones = Stones()
    val blackStones: Stones = Stones()

    fun putStone(
        point: OmokPoint,
        state: StoneColor,
    ) {
        when (state) {
            StoneColor.WHITE -> whiteStones + point
            StoneColor.BLACK -> blackStones + point
        }
    }

    fun getTotalStones(): Set<OmokPoint> {
        return whiteStones.stones + blackStones.stones
    }

    fun isFull(): Boolean {
        return getTotalStones().size == TOTAL_POINT_COUNT
    }

    fun getStones(nowTurn: StoneColor): Set<OmokPoint> {
        return when (nowTurn) {
            StoneColor.BLACK -> blackStones.stones
            StoneColor.WHITE -> whiteStones.stones
        }
    }

    companion object {
        const val DEFAULT_SIZE: Int = 15
        const val TOTAL_POINT_COUNT: Int = 225
    }
}
