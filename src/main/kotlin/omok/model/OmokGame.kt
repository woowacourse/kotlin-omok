package omok.model

import omok.model.event.PutEvent
import omok.model.state.GameState

class OmokGame(
    private var state: GameState,
    private val putEvent: PutEvent,
    private val onFinishGame: (Board, OmokStone) -> Unit,
) {
    fun play(onStartPut: (Board, OmokStone?) -> Unit) {
        var event = putEvent.onPutBlack
        while (!state.isFinished) {
            onStartPut(state.board, state.board.lastOrNull())
            state = state.placeStone(event)
            event = putEvent.reverse(event)
        }
        state.winner?.let { onFinishGame(state.board, it) }
    }
}
