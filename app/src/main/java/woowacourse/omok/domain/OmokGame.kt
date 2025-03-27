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
) {
    private var state: State = BlackTurn(board)
    private var lastStone: Stone? = null

    fun play(
        onTurn: (StoneColor, Point?) -> Unit,
        onPointSelected: () -> Point,
        onForbiddenMove: (String) -> Unit,
        onStonePlaced: (Stone) -> Unit,
    ) {
        when (val currentState = state) {
            is Playing -> processTurn(currentState, onTurn, onPointSelected, onForbiddenMove, onStonePlaced)
            is Finished -> {}
        }
    }

    fun finish(onFinished: (StoneColor?) -> Unit) {
        when (state) {
            is Playing -> {}
            is Finished -> onFinished(winner())
        }
    }

    private fun processTurn(
        state: Playing,
        onTurn: (StoneColor, Point?) -> Unit,
        onPointSelected: () -> Point,
        onForbiddenMove: (String) -> Unit,
        onStonePlaced: (Stone) -> Unit,
    ) {
        onTurn(state.stoneColor, lastStone?.point)
        val newStone = Stone(state.stoneColor, onPointSelected())
        when (val placeResult = state.place(newStone)) {
            is PlaceResult.ForbiddenMove -> {
                onForbiddenMove(placeResult.message)
            }
            is PlaceResult.Placed -> {
                this.state = placeResult.state
                onStonePlaced(newStone)
                lastStone = newStone
            }
        }
    }

    private fun winner(): StoneColor? =
        when (val currentState = state) {
            is Finished -> currentState.winnerColor
            else -> null
        }
}
