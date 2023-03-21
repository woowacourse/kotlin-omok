package omok.domain

import omok.domain.gameState.BlackTurn
import omok.domain.gameState.GameState

class OmokGame(private val listener: OmokGameListener) {
    fun play(
        gameState: GameState = BlackTurn(OmokBoard()),
        point: OmokPoint? = null,
    ): GameState {
        point ?: return gameState
        return runCatching { gameState.play(point) }
            .onSuccess { listener.onBoardShow(it.omokBoard) }
            .onFailure { listener.onError(it.message) }
            .getOrDefault(gameState)
    }
}
