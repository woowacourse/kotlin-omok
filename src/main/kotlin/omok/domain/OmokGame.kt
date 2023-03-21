package omok.domain

import omok.domain.gameState.BlackTurn
import omok.domain.gameState.GameState
import omok.domain.state.StoneState

private typealias OmokPointListener = (StoneState, OmokPoint?) -> OmokPoint
private typealias BoardStatusListener = (OmokBoard) -> Unit

class OmokGame {
    fun play(omokPointListener: OmokPointListener, boardStatusListener: BoardStatusListener) {
        var gameState: GameState = BlackTurn(OmokBoard(BOARD_X_SIZE, BOARD_Y_SIZE))
        var point: OmokPoint? = null
        while (gameState.isRunning) {
            point = omokPointListener(gameState.stoneState, point)
            runCatching { gameState = gameState.play(point) }.onFailure { println(it.message) }
            boardStatusListener(gameState.omokBoard)
        }
    }

    companion object {
        const val BOARD_X_SIZE = 15
        const val BOARD_Y_SIZE = 15
    }
}
