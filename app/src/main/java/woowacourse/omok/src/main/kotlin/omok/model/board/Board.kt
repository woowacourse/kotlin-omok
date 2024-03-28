package woowacourse.omok.src.main.kotlin.omok.model.board

import woowacourse.omok.src.main.kotlin.omok.model.position.Position
import woowacourse.omok.src.main.kotlin.omok.model.rule.RenjuRuleAdapter
import woowacourse.omok.src.main.kotlin.omok.model.stone.StoneType

object Board {
    private const val BOARD_SIZE = 15

    private val board = Array(BOARD_SIZE) { Array(BOARD_SIZE) { StoneType.NONE } }
    private lateinit var lastPosition: Position

    fun putStone(
        row: Int,
        column: Int,
        stoneType: StoneType,
    ) {
        board[row][column] = stoneType
    }

    fun checkRenjuRule(
        row: Int,
        column: Int,
    ): Boolean = RenjuRuleAdapter.checkRenjuRule(board, row, column)

    fun changeLstStonePosition(position: Position) {
        lastPosition = position
    }

    fun getLastStonePosition(): Position? =
        if (
            Board::lastPosition.isInitialized
        ) {
            lastPosition
        } else {
            null
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
                putStone(row, col, StoneType.NONE)
            }
        }
    }
}
