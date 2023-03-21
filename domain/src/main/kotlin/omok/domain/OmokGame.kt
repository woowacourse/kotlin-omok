package omok.domain

import omok.domain.gameState.BlackTurn
import omok.domain.gameState.GameState

class OmokGame {
    fun play(listener: OmokGameListener) {
        var gameState: GameState = BlackTurn(OmokBoard())
        var point: OmokPoint? = null

        while (gameState.isRunning) {
            listener.onBoardShow(gameState.omokBoard)
            point = listener.onPointRequest(gameState.stoneState, point)
            runCatching { gameState = gameState.play(point) }.onFailure { listener.onError(it.message) }
        }
    }
}
