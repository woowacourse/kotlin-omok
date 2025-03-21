package omok.model.board

import omok.model.Rule
import omok.model.stone.StoneState

class OmokBoard(
    private val rule: Rule,
) {
    private val board = mutableMapOf<Position, StoneState>()

    val keys get() = board.keys
    val values get() = board.values
    val ySize = Y_MAX_RANGE - Y_MIN_RANGE + 1
    val xSize = X_MAX_RANGE - X_MIN_RANGE + 1

    init {
        for (x in X_MIN_RANGE..X_MAX_RANGE) {
            for (y in Y_MIN_RANGE..Y_MAX_RANGE) {
                val position = Position.from(x, y)
                board[position] = StoneState.NONE
            }
        }
    }

    fun board(): Map<Position, StoneState> = board.toMap()

    private fun canPlaceStone(position: Position): Boolean = board[position] == StoneState.NONE

    fun doubleFour(position: Position) {
        board[position] = StoneState.DOUBLE_FOUR
    }

    fun doubleThree(position: Position) {
        board[position] = StoneState.DOUBLE_THREE
    }

    fun placeStone(
        position: Position,
        stoneState: StoneState,
    ) {
        if (canPlaceStone(position)) {
            board[position] = stoneState
        } else {
            throw IllegalArgumentException("해당위치에 돌이 존재합니다.")
        }
    }

    fun boardState(position: Position): StoneState = board[position] ?: throw IllegalArgumentException("잘못된 좌표입니다.")

    fun isOmok(
        position: Position,
        stone: StoneState,
        board: OmokBoard,
    ): Boolean = rule.findOmok(position, stone, board)

    companion object {
        const val Y_MAX_RANGE = 15
        const val Y_MIN_RANGE = 1
        const val X_MAX_RANGE = 15
        const val X_MIN_RANGE = 1
    }
}
