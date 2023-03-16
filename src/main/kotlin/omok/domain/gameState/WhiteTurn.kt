package omok.domain.gameState

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.OmokRule
import omok.domain.state.WhiteStoneState

class WhiteTurn(override val omokBoard: OmokBoard) : GameState {
    override val stoneState = WhiteStoneState
    override val isRunning: Boolean = true

    override fun play(point: OmokPoint): GameState = when {
        validateWinner(point) -> WhiteWin(omokBoard.placeStone(point, stoneState))
        checkForbidden(point) -> this
        else -> BlackTurn(omokBoard.placeStone(point, stoneState))
    }

    private fun checkForbidden(point: OmokPoint): Boolean = false

    private fun validateWinner(point: OmokPoint): Boolean {
        return OmokRule(omokBoard, stoneState).validateWhiteWin(point)
    }
}
