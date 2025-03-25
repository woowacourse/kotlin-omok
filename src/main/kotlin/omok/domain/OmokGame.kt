package omok.domain

import omok.domain.state.BlackTurn
import omok.domain.state.Playing
import omok.domain.state.State
import omok.domain.stone.StoneColor
import omok.domain.stone.Stones

class OmokGame(
    board: OmokBoard,
) {
    var state: State
        private set
    private var lastPoint: Point? = null

    init {
        state = BlackTurn(board)
    }

    fun play(
        onTurn: (StoneColor, Point?) -> Unit,
        onPointSelected: () -> Point,
        onBoardUpdated: (Stones, Stones) -> Unit,
    ) {
        while (state is Playing) {
            val playingState = state as Playing
            onTurn(playingState.stoneColor, lastPoint)
            val newPoint = onPointSelected()
            state = playingState.place(newPoint)
            lastPoint = newPoint
            onBoardUpdated(state.omokBoard.blackStones, state.omokBoard.whiteStones)
        }
    }
}
