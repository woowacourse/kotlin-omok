package domain.state

import domain.Stone

class WhiteTurn(private val blackStones: Set<Stone>, private val whiteStones: Set<Stone>) :
    Running(blackStones, whiteStones) {

    init {
        require(blackStones.size == (whiteStones.size + 1)) { WHITE_TURN_STONE_SIZE_ERROR }
    }

    override fun put(stone: Stone): State {
        checkAlreadyPlaced(stone)
        val nextWhiteStones = whiteStones + stone
        return if (nextWhiteStones.completeOmok()) {
            WhiteWin(blackStones, nextWhiteStones)
        } else {
            BlackTurn(
                blackStones,
                nextWhiteStones,
            )
        }
    }

    companion object {
        private const val WHITE_TURN_STONE_SIZE_ERROR = "백돌 차례에서는 흑돌이 백돌보다 1개 더 많아야 합니다."
    }
}
