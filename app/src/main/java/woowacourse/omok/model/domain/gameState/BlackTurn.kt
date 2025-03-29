package omok.model.domain.gameState

import omok.model.entity.Stone
import omok.model.entity.position.Position

class BlackTurn : Turn {
    override val stone: Stone = Stone.BLACK

    override fun nextGameState(
        gameState: GameState,
        position: Position,
    ): GameState = TurnResult(gameState, position).blackTurnResult()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BlackTurn

        return stone == other.stone
    }

    override fun hashCode(): Int = stone.hashCode()
}
