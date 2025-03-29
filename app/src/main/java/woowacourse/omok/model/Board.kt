package woowacourse.omok.model

import woowacourse.omok.model.adapter.RenjuRuleAdapter
import woowacourse.omok.model.game.FoulCondition
import woowacourse.omok.model.game.GameState
import woowacourse.omok.model.game.ViolationResult
import woowacourse.omok.model.stone.Point
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor
import woowacourse.omok.model.stone.Stones

class Board(
    val stones: Stones = Stones(),
    private val renjuRuleAdapter: RenjuRuleAdapter = RenjuRuleAdapter(),
) {
    fun currentStone(point: Point): Stone {
        val lastStone: Stone? = stones.lastStone
        val nextColor: StoneColor = (lastStone?.color ?: StoneColor.WHITE).reverse()
        return Stone(point, nextColor)
    }

    fun place(newStone: Stone) {
        stones.add(newStone)
        stones.setLastStone(newStone)
    }

    fun gameState(newStone: Stone): GameState {
        if (!renjuRuleAdapter.checkWin(stones.stones, newStone)) {
            return GameState.PLAYING
        }
        return when (newStone.color) {
            StoneColor.BLACK -> GameState.BLACK_OMOK
            StoneColor.WHITE -> GameState.WHITE_OMOK
        }
    }

    fun checkViolation(newStone: Stone): ViolationResult? {
        val invalidMoveResult: ViolationResult? = checkInvalidMove(newStone)
        if (invalidMoveResult != null) {
            return invalidMoveResult
        }

        val foulConditionResult: ViolationResult? = checkFoulCondition(newStone)
        if (foulConditionResult != null) {
            return foulConditionResult
        }
        return null
    }

    private fun checkInvalidMove(newStone: Stone): ViolationResult? =
        when {
            stones.stones.size == MAX_STONES_SIZE -> ViolationResult.InvalidMoveResult.FullBoard()
            stones.isOccupied(newStone) -> ViolationResult.InvalidMoveResult.OccupiedPoint()
            !isValidPoint(newStone.point) -> ViolationResult.InvalidMoveResult.OutOfBoard()
            else -> null
        }

    private fun checkFoulCondition(newStone: Stone): ViolationResult? =
        when (renjuRuleAdapter.checkAnyFoulCondition(stones.stones, newStone)) {
            FoulCondition.DOUBLE_FOUR -> ViolationResult.FoulConditionResult.DoubleFour()
            FoulCondition.DOUBLE_THREE -> ViolationResult.FoulConditionResult.DoubleThree()
            FoulCondition.OVERLINE -> ViolationResult.FoulConditionResult.Overline()
            FoulCondition.NONE -> null
        }

    private fun isValidPoint(point: Point): Boolean =
        point.row in MIN_BOARD_HEIGHT..MAX_BOARD_HEIGHT && point.col in MIN_BOARD_WIDTH..MAX_BOARD_WIDTH

    companion object {
        const val MIN_BOARD_HEIGHT = 1
        const val MIN_BOARD_WIDTH = 1
        const val MAX_BOARD_HEIGHT = 15
        const val MAX_BOARD_WIDTH = 15
        const val MAX_STONES_SIZE = MAX_BOARD_WIDTH * MAX_BOARD_HEIGHT
    }
}
