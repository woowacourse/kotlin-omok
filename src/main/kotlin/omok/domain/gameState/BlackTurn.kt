package omok.domain.gameState

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.omokRule.BlackWinRule
import omok.domain.omokRule.FourFourRule
import omok.domain.omokRule.ThreeThreeRule
import omok.domain.state.BlackStoneState
import omok.domain.util.OmokAdapter

class BlackTurn(override val omokBoard: OmokBoard) : GameState {
    override val stoneState = BlackStoneState
    override val isRunning: Boolean = true

    override fun play(point: OmokPoint): GameState {
        val adapted = OmokAdapter.adaptOmokBoard(omokBoard)
        val adaptedPoint = OmokAdapter.adaptOmokPoint(point)
        return when {
            BlackWinRule.validate(adapted, adaptedPoint) -> BlackWin(omokBoard.placeStone(point, stoneState))
            FourFourRule.validate(adapted, adaptedPoint) -> this
            ThreeThreeRule.validate(adapted, adaptedPoint) -> this
            else -> WhiteTurn(omokBoard.placeStone(point, stoneState))
        }
    }
}
