package omok.controller

import omok.domain.OmokBoard
import omok.domain.OmokGame
import omok.domain.OmokGameListener
import omok.domain.OmokPoint
import omok.domain.gameState.BlackTurn
import omok.domain.gameState.GameState

class OmokController(
    omokGameListener: OmokGameListener,
    var gameState: GameState = BlackTurn(OmokBoard()),
) {
    private val omokGame = OmokGame(omokGameListener)

    init {
        omokGame.initialize(gameState)
    }

    fun run(omokPoint: OmokPoint? = null) {
        gameState = omokGame.play(gameState, omokPoint)
    }
}
