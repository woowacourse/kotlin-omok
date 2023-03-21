package omok.domain

import omok.domain.gameState.BlackTurn
import omok.domain.gameState.GameState
import omok.domain.state.StoneState

private typealias OmokPointListener = (StoneState, OmokPoint?) -> OmokPoint
private typealias BoardStatusListener = (OmokBoard) -> Unit

class OmokGame {
    var point: OmokPoint? = null
    var gameState: GameState = BlackTurn(OmokBoard())
    fun play(omokPointListener: OmokPointListener, boardStatusListener: BoardStatusListener) {
        val tempPoint = omokPointListener(gameState.stoneState, point)
        runCatching { gameState = gameState.play(tempPoint) }.onFailure { println(it.message) }.onSuccess { point = tempPoint }
        boardStatusListener(gameState.omokBoard)
        when {
            gameState.isRunning -> play(omokPointListener, boardStatusListener)
            else -> return
        }
    }
}
