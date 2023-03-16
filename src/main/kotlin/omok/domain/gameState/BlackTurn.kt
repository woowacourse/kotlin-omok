package omok.domain.gameState

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.OmokRule
import omok.domain.state.BlackStoneState

class BlackTurn(override val omokBoard: OmokBoard) : GameState {
    override val stoneState = BlackStoneState
    override val isRunning: Boolean = true

    override fun play(point: OmokPoint): GameState = when {
        validateWinner(point) -> BlackWin(omokBoard.placeStone(point, stoneState))
        checkForbidden(point) -> this
        else -> WhiteTurn(omokBoard.placeStone(point, stoneState))
    }

    private fun checkForbidden(point: OmokPoint): Boolean {
        return OmokRule(omokBoard, stoneState).countOpenThrees(point) >= OmokRule.MIN_OPEN_THREES ||
            OmokRule(omokBoard, stoneState).countOpenFours(point) >= OmokRule.MIN_OPEN_FOURS
    }

    private fun validateWinner(point: OmokPoint): Boolean {
        return OmokRule(omokBoard, stoneState).validateBlackWin(point)
    }
}
