package domain.state

import domain.Point
import domain.Stones
import domain.WhiteStone
import domain.rule.RuleAdapter

class WhiteTurn(override val stones: Stones) :
    Running(stones) {

    init {
        require(stones.blackStones.size == (stones.whiteStones.size + 1)) { WHITE_TURN_STONE_SIZE_ERROR }
    }

    override fun put(point: Point, ruleAdapter: RuleAdapter): State {
        val stone = WhiteStone(point)
        checkAlreadyPlaced(point)
        val nextStones = stones.addStone(stone)
        return if (nextStones.whiteStones.completeOmok()) {
            WhiteWin(nextStones)
        } else {
            BlackTurn(
                nextStones
            )
        }
    }

    companion object {
        private const val WHITE_TURN_STONE_SIZE_ERROR = "백돌 차례에서는 흑돌이 백돌보다 1개 더 많아야 합니다."
    }
}
