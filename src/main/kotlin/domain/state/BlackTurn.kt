package domain.state

import domain.Rule
import domain.Stone

class BlackTurn(override val blackStones: Set<Stone>, override val whiteStones: Set<Stone>) :
    Running(blackStones, whiteStones) {
    init {
        require(blackStones.size == whiteStones.size) { STONE_COUNT_ERROR }
    }

    override fun put(stone: Stone): State {
        checkAlreadyPlaced(stone)
        Rule.checkPutBlackStone(blackStones, whiteStones, stone)
        val nextBlackStones = blackStones + stone
        return if (nextBlackStones.isCompletedOmok()) {
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
