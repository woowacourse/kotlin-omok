package omok.model.board

import omok.model.position.Position
import omok.model.result.PutResult
import omok.model.rule.LenjuRuleChecker
import omok.model.stone.StoneType

object Board {
    private const val BOARD_SIZE = 15

    private val board = Array(BOARD_SIZE) { Array(BOARD_SIZE) { StoneType.NONE } }
    private lateinit var lastPosition: Position

    fun putStone(
        row: Int,
        column: Int,
        stoneType: StoneType,
    ): PutResult {
        if (isAlreadyPlacedPosition(row, column)) return PutResult.Failure

        val lenjuRuleResult = checkRenjuRule(column, row)
        if (lenjuRuleResult == PutResult.Running) {
            board[row][column] = stoneType
        }
        return lenjuRuleResult
    }

    private fun isAlreadyPlacedPosition(
        row: Int,
        column: Int,
    ) = getStoneType(row, column) != StoneType.NONE

    fun checkRenjuRule(
        row: Int,
        column: Int,
    ): PutResult = LenjuRuleChecker.check(board, row, column)

    fun changeLastStonePosition(position: Position) {
        lastPosition = position
    }

    fun getLastStonePosition(): Position {
        return lastPosition
    }

    fun isLastPositionExist(): Boolean {
        return Board::lastPosition.isInitialized
    }

    fun isPositionInRange(
        row: Int,
        col: Int,
    ) = row in 0 until BOARD_SIZE && col in 0 until BOARD_SIZE

    fun isSameStone(
        column: Int,
        row: Int,
        stoneType: StoneType,
    ) = board[column][row] == stoneType

    fun getStoneType(
        row: Int,
        column: Int,
    ) = board[row][column]

    fun resetBoard() {
        repeat(BOARD_SIZE) { row ->
            repeat(BOARD_SIZE) { col ->
                board[row][col] = StoneType.NONE
            }
        }
    }
}
