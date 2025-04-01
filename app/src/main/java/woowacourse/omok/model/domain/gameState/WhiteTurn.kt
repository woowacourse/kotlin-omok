package omok.model.domain.gameState

import omok.model.entity.Stone
import omok.model.entity.position.Position

object WhiteTurn : Turn {
    override val stone: Stone = Stone.WHITE

    override fun nextGameState(
        gameState: GameState,
        position: Position,
    ): GameState {
        gameState.board.put(position, Stone.WHITE)
        return TurnResult(gameState, position).whiteTurnResult()
    }
}
