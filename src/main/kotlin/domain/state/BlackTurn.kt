package domain.state

import domain.Stone

class BlackTurn(private val blackStones: Set<Stone>, private val whiteStones: Set<Stone>) :
    Running(blackStones, whiteStones) {
    init {
        require(blackStones.size == whiteStones.size) { STONE_COUNT_ERROR }
    }

    override fun put(stone: Stone): State {
        checkAlreadyPlaced(stone)
        val nextBlackStones = blackStones + stone
        return if (nextBlackStones.completeOmok()) {
            BlackWin(
                nextBlackStones,
                whiteStones,
            )
        } else {
            WhiteTurn(nextBlackStones, whiteStones)
        }
    }

    companion object {
        private const val STONE_COUNT_ERROR = "흑돌 차례에서는 흑돌과 백돌의 개수가 같아야 합니다."
    }
}
