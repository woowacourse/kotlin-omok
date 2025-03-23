package omok.model.board

import omok.model.player.state.BlackPlayerState
import omok.model.player.state.PlayerState
import omok.model.player.state.WhitePlayerState

class OmokBoard {
    val board = mutableMapOf<Position, PositionState>()
    val keys get() = board.keys
    val ySize = Y_SIZE
    val xSize = X_SIZE

    init {
        for (x in X_MIN_RANGE..X_MAX_RANGE) {
            for (y in Y_MIN_RANGE..Y_MAX_RANGE) {
                val position = Position(X(x), Y(y))
                board[position] = PositionState.NONE
            }
        }
    }

    private fun canPlaceStone(position: Position): Boolean = board[position] == PositionState.NONE

    fun forbidden(position: Position) {
        board[position] = PositionState.FORBIDDEN
        throw IllegalArgumentException("금수입니다. 다른 자리에 착수해주세요.")
    }

    fun placeStone(
        position: Position,
        playerState: PlayerState,
    ) {
        if (canPlaceStone(position)) {
            when (playerState) {
                is BlackPlayerState -> board[position] = PositionState.BLACK_POSITION
                is WhitePlayerState -> board[position] = PositionState.WHITE_POSITION
            }
        } else {
            throw IllegalArgumentException("해당위치에 돌이 존재합니다.")
        }
    }

    fun boardState(position: Position): PositionState = board[position] ?: throw IllegalArgumentException("잘못된 좌표입니다.")

    companion object {
        private const val Y_MAX_RANGE = 15
        private const val Y_MIN_RANGE = 1
        private const val X_MAX_RANGE = 15
        private const val X_MIN_RANGE = 1
        private const val Y_SIZE = Y_MAX_RANGE - Y_MIN_RANGE + 1
        private const val X_SIZE = X_MAX_RANGE - X_MIN_RANGE + 1
    }
}
