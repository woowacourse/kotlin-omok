package domain.state

import domain.Stone

class WhiteWin(override val blackStones: Set<Stone>, override val whiteStones: Set<Stone>) :
    Finished(blackStones, whiteStones) {

    init {
        require(blackStones.size == whiteStones.size) { STONE_COUNT_ERROR }
    }

    companion object {
        private const val STONE_COUNT_ERROR = "백돌 승리 상태에서는 흑돌과 백돌의 개수가 같아야 합니다."
    }
}
