package domain.state

import domain.stone.Stone

class BlackTurn(override val blackStones: Set<Stone>, override val whiteStones: Set<Stone>) :
    Running(blackStones, whiteStones) {
    init {
        require(blackStones.size == whiteStones.size) { STONE_COUNT_ERROR }
    }

    override fun put(stone: Stone): State {
        require(canPut(stone)) { BLACK_STONE_CANT_PUT_ERROR }
        val nextBlackStones = blackStones + stone
        return if (nextBlackStones.isCompletedOmok()) {
            BlackWin(nextBlackStones, whiteStones)
        } else {
            WhiteTurn(nextBlackStones, whiteStones)
        }
    }

    companion object {
        private const val STONE_COUNT_ERROR = "흑돌 차례에서는 흑돌과 백돌의 개수가 같아야 합니다."
        private const val BLACK_STONE_CANT_PUT_ERROR = "해당 좌표에 흑돌을 둘 수 없습니다."
    }
}