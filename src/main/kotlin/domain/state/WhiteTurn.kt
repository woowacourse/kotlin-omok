package domain.state

import domain.stone.Stone

class WhiteTurn(override val blackStones: Set<Stone>, override val whiteStones: Set<Stone>) :
    Running(blackStones, whiteStones) {

    init {
        require(blackStones.size == (whiteStones.size + 1)) { WHITE_TURN_STONE_SIZE_ERROR }
    }

    override fun put(stone: Stone): State {
        require(canPut(stone)) { WHITE_STONE_CANT_PUT_ERROR }
        val nextWhiteStones = whiteStones + stone
        return if (nextWhiteStones.isCompletedOmok()) {
            WhiteWin(blackStones, nextWhiteStones)
        } else {
            BlackTurn(blackStones, nextWhiteStones)
        }
    }

    companion object {
        private const val WHITE_TURN_STONE_SIZE_ERROR = "백돌 차례에서는 흑돌이 백돌보다 1개 더 많아야 합니다."
        private const val WHITE_STONE_CANT_PUT_ERROR = "해당 좌표에 백돌을 둘 수 없습니다."
    }
}
