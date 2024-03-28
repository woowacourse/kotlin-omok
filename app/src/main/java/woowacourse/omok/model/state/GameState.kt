package woowacourse.omok.model.state

import woowacourse.omok.model.Board
import woowacourse.omok.model.Position

sealed class GameState(val board: Board) {
    val winner get() = if (this is Finish) board.lastStone else null

    abstract fun put(position: Position): GameState

    class Finish(board: Board) : GameState(board) {
        override fun put(position: Position): GameState {
            error("게임이 이미 종료됐습니다.")
        }
    }
}
