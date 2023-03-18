package omok.domain

import omok.domain.gameState.BlackTurn
import omok.domain.gameState.GameState
import omok.domain.state.StoneState

class OmokGame {
    fun play(getPoint: (StoneState, OmokPoint?) -> OmokPoint, output: (OmokBoard) -> Unit) {
        var gameState: GameState = BlackTurn(OmokBoard())
        var point: OmokPoint? = null
        while (gameState.isRunning) {
            output(gameState.omokBoard)
            point = getPoint(gameState.stoneState, point)
            runCatching { gameState = gameState.play(point) }.onFailure { println(it.message) }
        }
    }
}
