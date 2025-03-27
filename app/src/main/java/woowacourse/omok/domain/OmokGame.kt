package woowacourse.omok.domain

import woowacourse.omok.domain.state.BlackTurn
import woowacourse.omok.domain.state.Finished
import woowacourse.omok.domain.state.PlaceResult
import woowacourse.omok.domain.state.Playing
import woowacourse.omok.domain.state.State
import woowacourse.omok.domain.stone.StoneColor

class OmokGame(
    board: OmokBoard,
) {
    private var _state: State
    val state get() = _state
    private var lastPoint: Point? = null

    init {
        _state = BlackTurn(board)
    }

    fun play(
        onTurn: (StoneColor, Point?) -> Unit,
        onPointSelected: () -> Point,
        onBoardUpdated: (OmokBoard) -> Unit,
    ) {
        when (val currentState = _state) {
            is Playing -> processTurn(currentState, onTurn, onPointSelected, onBoardUpdated)
            is Finished -> {}
        }
    }

    fun winner(): StoneColor? =
        when (val currentState = _state) {
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
        when (val placeResult = playingState.place(newPoint)) {
            is PlaceResult.ForbiddenMove -> {}
            is PlaceResult.Placed -> _state = placeResult.state
        }
        lastPoint = newPoint
        onBoardUpdated(_state.omokBoard)
    }
}
