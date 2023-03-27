package omok.domain

import omok.domain.gameState.BlackTurn
import omok.domain.gameState.GameState

class OmokGame(private val listener: OmokGameListener) {
    fun initialize(
        gameState: GameState = BlackTurn(OmokBoard()),
    ) {
        listener.onStartGame()
        listener.onProgressGame(gameState, null)
    }

    fun play(
        gameState: GameState = BlackTurn(OmokBoard()),
        point: OmokPoint? = null,
    ): GameState {
        point ?: return gameState
        return runCatching { gameState.play(point) }
            .onSuccess { listener.onProgressGame(it, point) }
            .onFailure { listener.onError(it.message) }
            .getOrDefault(gameState)
    }
}
