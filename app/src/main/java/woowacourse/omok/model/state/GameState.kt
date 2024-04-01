package woowacourse.omok.model.state

import woowacourse.omok.model.Board
import woowacourse.omok.model.Position

sealed class GameState(val board: Board) {
    abstract val isFinished: Boolean
    val winner get() = if (this is Finish) board.lastStone else null

    abstract fun put(position: Position): GameState

    class Finish(board: Board) : GameState(board) {
        override val isFinished: Boolean = true

        override fun put(position: Position): GameState {
            return this
        }
    }
}
