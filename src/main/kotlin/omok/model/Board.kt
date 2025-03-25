package omok.model

import omok.model.adapter.RenjuRuleAdapter
import omok.model.game.FoulCondition
import omok.model.game.FoulConditionResult
import omok.model.game.GameState
import omok.model.game.InvalidMoveResult
import omok.model.stone.Point
import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.model.stone.Stones

class Board(
    val stones: Stones = Stones(),
    private val renjuRuleAdapter: RenjuRuleAdapter = RenjuRuleAdapter(),
) {
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

    fun checkFoulCondition(newStone: Stone): FoulConditionResult? =
        when (renjuRuleAdapter.checkAnyFoulCondition(stones.stones, newStone)) {
            FoulCondition.DOUBLE_THREE -> FoulConditionResult.DoubleThree()
            FoulCondition.DOUBLE_FOUR -> FoulConditionResult.DoubleFour()
            FoulCondition.OVERLINE -> FoulConditionResult.Overline()
            FoulCondition.NONE -> null
        }

    fun checkInvalidMove(newStone: Stone): InvalidMoveResult? =
        when {
            stones.stones.size == MAX_STONES_SIZE -> InvalidMoveResult.FullBoard()
            stones.isOccupied(newStone) -> InvalidMoveResult.OccupiedPoint()
            !isValidPoint(newStone.point) -> InvalidMoveResult.OutOfBoard()
            else -> null
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
