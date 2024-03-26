package omok.model

import omok.model.event.PutEvent
import omok.model.state.GameState

class OmokGame(
    private var state: GameState,
    private val putEvent: PutEvent,
    private val onFinishGame: (Board, OmokStone) -> Unit,
) {
    fun play(onStartPut: (Board, OmokStone?) -> Unit) {
        while (state !is GameState.Finish) {
            onStartPut(state.board, state.board.lastStone)
            state = state.put(putEvent)
        }
        state.winner?.let { onFinishGame(state.board, it) }
    }
}
