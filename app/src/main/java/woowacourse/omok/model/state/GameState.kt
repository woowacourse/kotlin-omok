package omok.model.state

import omok.model.Board
import omok.model.event.PutEvent

sealed class GameState(val board: Board) {
    val winner get() = if (this is Finish) board.lastStone else null

    abstract fun put(putEvent: PutEvent): GameState

    class Finish(board: Board) : GameState(board) {
        override fun put(putEvent: PutEvent): GameState {
            error("게임이 이미 종료됐습니다.")
        }
    }
}
