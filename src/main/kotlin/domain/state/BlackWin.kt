package domain.state

import domain.Stone

class BlackWin(override val blackStones: Set<Stone>, override val whiteStones: Set<Stone>) :
    Finished(blackStones, whiteStones) {
    init {
        require(blackStones.size == whiteStones.size + 1) { STONE_COUNT_ERROR }
    }

    companion object {
        private const val STONE_COUNT_ERROR = "흑돌 승리 상태에서는 흑돌의 개수가 백돌의 개수보다 1개 더 많아야 합니다."
    }
}
