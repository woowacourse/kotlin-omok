package omok

import omok.gameState.BlackTurn
import omok.gameState.GameState
import omok.state.StoneState

class OmokGame {
    var gameState: GameState = BlackTurn(OmokBoard())
        private set

    fun play(point: OmokPoint): StoneState {
        require(point in gameState.omokBoard.value) { ERROR_POINT_OVER.format(point.x, point.y) }
        val currentState = gameState.stoneState
        gameState = gameState.play(point)
        return currentState
    }

    companion object {
        private const val ERROR_POINT_OVER = "(%s, %s)는 오목판을 벗어납니다."
    }
}
