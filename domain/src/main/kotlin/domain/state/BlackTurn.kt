package domain.state

import domain.stone.BlackStone
import domain.stone.Point
import domain.stone.Stones
import domain.rule.Referee

class BlackTurn(override val stones: Stones) :
    Running(stones) {
    init {
        require(stones.blackStones.size == stones.whiteStones.size) { STONE_COUNT_ERROR }
    }

    override fun put(point: Point, referee: Referee): State {
        val stone = BlackStone(point)
        checkAlreadyPlaced(point)
        referee.checkStone(stones, stone)
        val nextStones = stones.addStone(stone)
        return if (nextStones.blackStones.completeOmok()) {
            BlackWin(
                nextStones
            )
        } else {
            WhiteTurn(nextStones)
        }
    }

    companion object {
        private const val STONE_COUNT_ERROR = "흑돌 차례에서는 흑돌과 백돌의 개수가 같아야 합니다."
    }
}
