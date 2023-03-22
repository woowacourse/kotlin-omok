package omok.domain

import omok.domain.gameState.GameState

interface OmokGameListener {
    fun onOmokStart()
    fun onBoardShow(gameState: GameState, omokPoint: OmokPoint?)
    fun onError(message: String?)
}
