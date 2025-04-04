package woowacourse.omok.domain

import woowacourse.omok.domain.state.BlackTurn
import woowacourse.omok.domain.state.Finished
import woowacourse.omok.domain.state.PlaceResult
import woowacourse.omok.domain.state.Playing
import woowacourse.omok.domain.state.State
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.StoneColor

class OmokGame(
    board: OmokBoard,
    state: State = BlackTurn(board),
) {
    var state: State = state
        private set
    private var lastStone: Stone? = null

    fun play(
        onTurn: (StoneColor, Point?) -> Unit,
        onPointSelected: () -> Point,
        onForbiddenMove: (PlaceResult.ForbiddenMove) -> Unit,
        onStonePlaced: (OmokBoard, Stone) -> Unit,
    ) {
        if (state is Playing) {
            processTurn((state as Playing), onTurn, onPointSelected, onForbiddenMove, onStonePlaced)
        }
    }

    fun finish(onFinished: (StoneColor?) -> Unit) {
        if (state is Finished) {
            onFinished((state as Finished).winnerColor)
        }
    }

    private fun processTurn(
        playingState: Playing,
        onTurn: (StoneColor, Point?) -> Unit,
        onPointSelected: () -> Point,
        onForbiddenMove: (PlaceResult.ForbiddenMove) -> Unit,
        onStonePlaced: (OmokBoard, Stone) -> Unit,
    ) {
        onTurn(playingState.stoneColor, lastStone?.point)
        val newStone = Stone(playingState.stoneColor, onPointSelected())
        handlePlaceResult(playingState.place(newStone), newStone, onForbiddenMove, onStonePlaced)
    }

    private fun handlePlaceResult(
        placeResult: PlaceResult,
        newStone: Stone,
        onForbiddenMove: (PlaceResult.ForbiddenMove) -> Unit,
        onStonePlaced: (OmokBoard, Stone) -> Unit,
    ) {
        when (placeResult) {
            is PlaceResult.ForbiddenMove -> onForbiddenMove(placeResult)
            is PlaceResult.Placed -> {
                state = placeResult.state
                lastStone = newStone
                onStonePlaced(state.omokBoard, newStone)
            }
        }
    }
}
