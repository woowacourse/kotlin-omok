package omok.domain

import omok.domain.state.BlackTurn
import omok.domain.state.Playing
import omok.domain.state.Ready
import omok.domain.state.State
import omok.domain.state.WhiteTurn
import omok.domain.stone.StoneColor

class Board(
    state: State = Ready(),
    val size: Int = DEFAULT_BOARD_SIZE,
) {
    init {
        require(size >= MINIMUM_BOARD_SIZE) { ERROR_INVALID_BOARD_SIZE }
    }

    var state = state
        private set

    fun playOmok(
        onTurn: (StoneColor, Point?) -> Unit,
        onPointInput: () -> Point,
        onBoardUpdated: (Set<Point>, Set<Point>) -> Unit,
    ) {
        while (state is Playing) {
            val playingState = state as Playing

            when (playingState) {
                is WhiteTurn -> onTurn(playingState.nextStoneColor(), playingState.blackStones.lastStonePoint)
                is BlackTurn -> onTurn(playingState.nextStoneColor(), playingState.whiteStones.lastStonePoint)
                else -> onTurn(playingState.nextStoneColor(), null)
            }

            state = playingState.place(onPointInput(), size)
            onBoardUpdated(state.blackStones.points, state.whiteStones.points)
        }
    }

    companion object {
        const val DEFAULT_BOARD_SIZE = 15
        private const val MINIMUM_BOARD_SIZE = 5
        private const val ERROR_INVALID_BOARD_SIZE = "[ERROR] 오목판의 사이즈는 최소 5x5이어야 합니다."
    }
}
