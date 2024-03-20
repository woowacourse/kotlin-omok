package omok.model

import omok.model.state.GameState

class OmokGame(private var state: GameState) {
    private var finishState: GameState.Finish? = null
    fun play(onPut: () -> Position, onFinish: (Board) -> Unit) {
        val point = onPut()
        val newState = (state as GameState.Running).put(point)
        state = newState
//        finishState = GameState.Finish(state.getBoard())
    }

    private fun playByState(position: Position) {

    }
}
