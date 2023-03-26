package omok.domain

import omok.domain.gameState.GameState

interface OmokGameListener {
    fun onStartGame()
    fun onProgressGame(gameState: GameState, omokPoint: OmokPoint?)
    fun onError(message: String?)
}
