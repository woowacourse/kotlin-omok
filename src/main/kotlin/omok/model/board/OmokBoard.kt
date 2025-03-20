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
        for (x in 1..15) {
            for (y in 1..15) {
                val position = Position(X(x), Y(y))
                board[position] = StoneState.NONE
            }
        }
    }

    fun board(): Map<Position, StoneState> = board.toMap()

    private fun canPlaceStone(position: Position): Boolean = board[position] == StoneState.NONE

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
