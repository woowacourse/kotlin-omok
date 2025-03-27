package woowacourse.omok.domain

import woowacourse.omok.domain.state.BlackTurn
import woowacourse.omok.domain.state.Finished
import woowacourse.omok.domain.state.Playing
import woowacourse.omok.domain.state.State
import woowacourse.omok.domain.stone.StoneColor

class OmokGame(
    board: OmokBoard,
) {
    private var state: State
    private var lastPoint: Point? = null

    init {
        state = BlackTurn(board)
    }

    fun play(
        onTurn: (StoneColor, Point?) -> Unit,
        onPointSelected: () -> Point,
        onBoardUpdated: (OmokBoard) -> Unit,
    ) {
        while (true) {
            when (val currentState = state) {
                is Playing -> processTurn(currentState, onTurn, onPointSelected, onBoardUpdated)
                is Finished -> break
            }
        }
    }

    fun winner(): StoneColor? =
        when (val currentState = state) {
            is Finished -> currentState.winnerColor
            else -> null
        }

    private fun processTurn(
        playingState: Playing,
        onTurn: (StoneColor, Point?) -> Unit,
        onPointSelected: () -> Point,
        onBoardUpdated: (OmokBoard) -> Unit,
    ) {
        onTurn(playingState.stoneColor, lastPoint)
        val newPoint = onPointSelected()
        state = playingState.place(newPoint)
        lastPoint = newPoint
        onBoardUpdated(state.omokBoard)
    }
}
