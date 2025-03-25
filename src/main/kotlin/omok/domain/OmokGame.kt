package omok.domain

import omok.domain.state.BlackTurn
import omok.domain.state.Finished
import omok.domain.state.Playing
import omok.domain.state.State
import omok.domain.stone.StoneColor

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
        while (state is Playing) {
            val playingState = state as Playing
            onTurn(playingState.stoneColor, lastPoint)
            val newPoint = onPointSelected()
            state = playingState.place(newPoint)
            lastPoint = newPoint
            onBoardUpdated(state.omokBoard)
        }
    }

    fun winner(): StoneColor? =
        if (state is Finished) {
            (state as Finished).winnerColor
        } else {
            null
        }
}
