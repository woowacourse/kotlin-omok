package woowacourse.omok.model.board

import woowacourse.omok.model.board.OmokBoardConfig.X_MAX_RANGE
import woowacourse.omok.model.board.OmokBoardConfig.X_MIN_RANGE
import woowacourse.omok.model.board.OmokBoardConfig.Y_MAX_RANGE
import woowacourse.omok.model.board.OmokBoardConfig.Y_MIN_RANGE
import woowacourse.omok.model.stone.StoneColor

class OmokBoard(
    val xSize: Int = X_MAX_RANGE,
    val ySize: Int = Y_MAX_RANGE,
) {
    val board = mutableMapOf<Position, PositionState>()
    val keys get() = board.keys

    init {
        reset()
    }

    fun placeStone(
        position: Position,
        currentStoneColor: StoneColor,
    ) {
        if (canPlaceStone(position, currentStoneColor)) {
            board[position] =
                when (currentStoneColor) {
                    StoneColor.BLACK -> PositionState.BLACK_POSITION
                    StoneColor.WHITE -> PositionState.WHITE_POSITION
                }
        }
    }

    fun forbidden(
        stoneColor: StoneColor,
        position: Position,
    ) {
        if (stoneColor == StoneColor.BLACK) board[position] = PositionState.FORBIDDEN
    }

    fun boardState(position: Position): PositionState = board[position] ?: throw IllegalArgumentException("잘못된 좌표입니다.")

    fun reset() {
        for (x in X_MIN_RANGE..X_MAX_RANGE) {
            for (y in Y_MIN_RANGE..Y_MAX_RANGE) {
                val position = Position(x, y)
                board[position] = PositionState.NONE
            }
        }
    }

    private fun canPlaceStone(
        position: Position,
        currentStoneColor: StoneColor,
    ): Boolean {
        val currentState = board[position]

        return when (currentState) {
            PositionState.NONE -> true
            PositionState.FORBIDDEN -> currentStoneColor == StoneColor.WHITE
            else -> false
        }
    }
}
