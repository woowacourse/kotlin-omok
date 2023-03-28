package omok

import omok.gameState.BlackTurn
import omok.gameState.GameState

class OmokGame(
    state: GameState = BlackTurn(OmokBoard()),
    val onSuccessProcess: (OmokPoint) -> Unit = {},
    val onFailedProcess: (String) -> Unit = {}
) {
    var gameState: GameState = state
        private set

    fun play(point: OmokPoint): GameState {
        require(point in gameState.omokBoard.value) { ERROR_POINT_OVER.format(point.x, point.y) }
        val currentState = gameState

        runCatching {
            gameState = gameState.play(point)
        }
            .onFailure {
                onFailedProcess(it.message.toString())
            }
            .onSuccess {
                onSuccessProcess(point)
            }
        return currentState
    }

    companion object {
        private const val ERROR_POINT_OVER = "(%s, %s)는 오목판을 벗어납니다."
    }
}
