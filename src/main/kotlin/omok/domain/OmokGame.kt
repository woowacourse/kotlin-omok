package omok.domain

import omok.domain.state.BlackTurn
import omok.domain.state.Playing
import omok.domain.state.State
import omok.domain.stone.StoneColor

class OmokGame(
    val boardSize: Int = DEFAULT_BOARD_SIZE,
    state: State = BlackTurn(boardSize),
) {
    var state = state
        private set

    init {
        require(boardSize >= MINIMUM_BOARD_SIZE) { ERROR_INVALID_BOARD_SIZE }
    }

    fun play(
        onTurn: (StoneColor, Point?) -> Unit,
        onPointSelected: () -> Point,
        onBoardUpdated: (Set<Point>, Set<Point>) -> Unit,
    ) {
        while (state is Playing) {
            val playingState = state as Playing
            onTurn(playingState.nextStoneColor(), playingState.lastStonePoint())
            state = playingState.place(onPointSelected())
            onBoardUpdated(state.blackStones.points, state.whiteStones.points)
        }
    }

    companion object {
        const val DEFAULT_BOARD_SIZE = 15
        private const val MINIMUM_BOARD_SIZE = 5
        private const val ERROR_INVALID_BOARD_SIZE = "[ERROR] 오목판의 사이즈는 최소 5x5이어야 합니다."
    }
}
