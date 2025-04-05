package woowacourse.omok.model

import woowacourse.omok.model.adapter.RuleAdapter
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.gameState.GameState
import woowacourse.omok.model.gameState.PutState
import woowacourse.omok.model.position.Position

class OmokGame(
    val board: Board,
) {
    var currentState: GameState = GameState.Playing(Stone.BLACK)
        private set
    var lastPosition: Position? = null
        private set
    var lastPlaying: GameState.Playing = GameState.Playing(Stone.BLACK)
        private set

    fun setTurn(previousStone: Stone) {
        lastPlaying =
            when (previousStone) {
                Stone.BLACK -> GameState.Playing(Stone.WHITE)
                Stone.WHITE -> GameState.Playing(Stone.BLACK)
            }
        currentState = lastPlaying
    }

    fun putStone(position: Position): PutState {
        lastPlaying = currentState as? GameState.Playing ?: return PutState.GameOverCantPutStone
        val putState = board.put(position, lastPlaying.stone)

        if (putState == PutState.CanPutStone) {
            currentState = RuleAdapter.getState(board, position, lastPlaying.stone)
            updateLastPosition(position, lastPlaying)
        }

        return putState
    }

    private fun updateLastPosition(
        position: Position,
        lastPlaying: GameState.Playing,
    ) {
        lastPosition =
            if (forbiddenPosition(currentState, lastPlaying)) lastPosition else position
    }

    private fun forbiddenPosition(
        currentTurn: GameState,
        lastPlaying: GameState.Playing?,
    ): Boolean = currentTurn == lastPlaying

    fun isPlaying(): Boolean = currentState is GameState.Playing

    fun isDraw(): Boolean = board.isFull()
}
