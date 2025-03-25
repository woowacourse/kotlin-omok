package omok.domain

import omok.domain.state.BlackTurn
import omok.domain.state.Finished
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
        while (true) {
            val currentState = state

            if (currentState is Playing) {
                when (currentState) {
                    is WhiteTurn -> onTurn(currentState.nextStoneColor(), currentState.blackStones.lastStonePoint)
                    is BlackTurn -> onTurn(currentState.nextStoneColor(), currentState.whiteStones.lastStonePoint)
                    else -> onTurn(currentState.nextStoneColor(), null)
                }
                state = currentState.place(onPointInput(), size, onBoardUpdated)
            }

            if (state is Finished) break
        }
    }

    companion object {
        const val DEFAULT_BOARD_SIZE = 15
        private const val MINIMUM_BOARD_SIZE = 5
        private const val ERROR_INVALID_BOARD_SIZE = "[ERROR] 오목판의 사이즈는 최소 5x5이어야 합니다."
    }
}
