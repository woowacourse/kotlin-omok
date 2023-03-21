package omok.controller

import omok.domain.OmokBoard
import omok.domain.OmokGame
import omok.domain.OmokGameListener
import omok.domain.OmokPoint
import omok.domain.gameState.BlackTurn
import omok.domain.gameState.GameState
import omok.domain.gameState.WhiteTurn
import omok.domain.state.BlackStoneState
import omok.domain.state.EmptyStoneState
import omok.domain.state.StoneState

class OmokController(
    omokGameListener: OmokGameListener,
    omokBoard: OmokBoard = OmokBoard(),
    omokPoint: OmokPoint? = null,
    stoneState: StoneState = BlackStoneState,
) {
    private val omokGame = OmokGame(omokGameListener)
    var gameState: GameState = BlackTurn(omokBoard)

    init {
        omokGameListener.onOmokStart()
        omokGameListener.onBoardShow(gameState.omokBoard, null)
        if (omokPoint != null && stoneState != EmptyStoneState) {
            gameState = when (stoneState) {
                BlackStoneState -> BlackTurn(gameState.omokBoard)
                else -> WhiteTurn(gameState.omokBoard)
            }
            gameState = omokGame.play(gameState, omokPoint)
        }
    }

    fun run(omokPoint: OmokPoint? = null) {
        gameState = omokGame.play(gameState, omokPoint)
    }
}
