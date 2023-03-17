package omok.domain.gameState

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.omokRule.WhiteWinRule
import omok.domain.state.WhiteStoneState
import omok.domain.util.OmokAdapter

class WhiteTurn(override val omokBoard: OmokBoard) : GameState {
    override val stoneState = WhiteStoneState
    override val isRunning: Boolean = true

    override fun play(point: OmokPoint): GameState {
        val adapted = OmokAdapter.adaptOmokBoard(omokBoard)
        val adaptedPoint = OmokAdapter.adaptOmokPoint(point)
        return when {
            WhiteWinRule.validate(adapted, adaptedPoint) -> WhiteWin(omokBoard.placeStone(point, stoneState))
            WhiteWinRule.validate(adapted, adaptedPoint) -> this
            else -> BlackTurn(omokBoard.placeStone(point, stoneState))
        }
    }
}
