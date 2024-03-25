package omok.model.board

import omok.model.position.Position
import omok.model.stone.StoneType

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

    fun isBoardEdge(currentCoordinate: Int) = currentCoordinate in listOf(0, BOARD_SIZE - 1)

    fun getStoneType(
        row: Int,
        column: Int,
    ) = board[row][column]
}
