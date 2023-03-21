package omok.controller

import omok.domain.OmokBoard
import omok.domain.OmokGame
import omok.domain.OmokGameListener
import omok.domain.OmokPoint
import omok.domain.gameState.BlackTurn
import omok.domain.gameState.GameState

class OmokController(omokGameListener: OmokGameListener) {
    private val omokGame = OmokGame(omokGameListener)
    var gameState: GameState = BlackTurn(OmokBoard())
        private set

    init {
        omokGameListener.onOmokStart()
        omokGameListener.onBoardShow(gameState.omokBoard)
    }

    fun run(omokPoint: OmokPoint? = null) {
        gameState = omokGame.play(gameState, omokPoint)
    }
}
