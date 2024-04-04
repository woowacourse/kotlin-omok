package woowacourse.omok.model.game

import woowacourse.omok.model.event.OnPlaceListener
import woowacourse.omok.model.omok.Board
import woowacourse.omok.model.omok.OmokStone
import woowacourse.omok.model.state.GameState

class OmokGame(
    private var state: GameState,
    private val placeListener: OnPlaceListener,
    private val onFinishGame: (Board, OmokStone) -> Unit,
) {
    fun play(onStartPut: (Board, OmokStone?) -> Unit) {
        while (!state.isFinished) {
            onStartPut(state.board, state.board.lastStone)
            val position = placeListener.onPlace()
            state = state.put(position)
        }
        state.winner?.let { onFinishGame(state.board, it) }
    }
}
