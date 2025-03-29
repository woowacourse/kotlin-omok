package omok.model.domain.gameState

import omok.model.entity.Stone
import omok.model.entity.board.Board
import omok.model.entity.position.Position

data class GameState(
    val board: Board,
    val playing: Boolean = true,
    private val turn: Turn = BlackTurn(),
) {
    val stone: Stone = turn.stone

    fun play(position: Position): GameState {
        if (!playing) throw IllegalStateException("Game is already finished. current state: $this")
        return turn.nextGameState(this, position)
    }
}

interface Turn {
    val stone: Stone

    fun nextGameState(
        gameState: GameState,
        position: Position,
    ): GameState
}
