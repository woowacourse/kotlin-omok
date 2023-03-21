package domain.state

import domain.Stones

class WhiteWin(override val stones: Stones) :
    Finished(stones) {

    init {
        require(stones.blackStones.size == stones.whiteStones.size) { STONE_COUNT_ERROR }
    }

    companion object {
        private const val STONE_COUNT_ERROR = "백돌 승리 상태에서는 흑돌과 백돌의 개수가 같아야 합니다."
    }
}
