package omok.controller

import omok.domain.OmokBoard
import omok.domain.OmokGame
import omok.domain.OmokGameListener
import omok.domain.OmokPoint
import omok.domain.gameState.BlackTurn
import omok.domain.gameState.GameState

class OmokController(omokGameListener: OmokGameListener, omokBoard: OmokBoard = OmokBoard()) {
    private val omokGame = OmokGame(omokGameListener)
    var gameState: GameState = BlackTurn(omokBoard)

    init {
        omokGameListener.onOmokStart()
        omokGameListener.onBoardShow(gameState.omokBoard, null)
    }

    fun run(omokPoint: OmokPoint? = null) {
        gameState = omokGame.play(gameState, omokPoint)
    }
}
