package omok.domain

import omok.domain.gameState.BlackTurn
import omok.domain.gameState.GameState

class OmokGame {
    fun play(getPoint: (String, OmokPoint?) -> OmokPoint, output: (OmokBoard) -> Unit) {
        var gameState: GameState = BlackTurn(OmokBoard())
        var point: OmokPoint? = null
        while (gameState.isRunning) {
            point = getPoint(gameState.stoneState.korean, point)
            runCatching { gameState = gameState.play(point) }.onFailure { println(it.message) }
            output(gameState.omokBoard)
        }
    }
}
