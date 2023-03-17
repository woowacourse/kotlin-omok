package omok.domain.gameState

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.omokRule.BlackWinRule
import omok.domain.omokRule.FourFourRule
import omok.domain.omokRule.ThreeThreeRule
import omok.domain.state.BlackStoneState
import omok.domain.state.WhiteStoneState

class BlackTurn(override val omokBoard: OmokBoard) : GameState {
    override val stoneState = BlackStoneState
    override val isRunning: Boolean = true

    override fun play(point: OmokPoint): GameState = when {
        validateWinner(point) -> BlackWin(omokBoard.placeStone(point, stoneState))
        checkForbidden(point) -> this
        else -> WhiteTurn(omokBoard.placeStone(point, stoneState))
    }

    private fun checkForbidden(point: OmokPoint): Boolean {
        val adapted = adaptOmokBoard(omokBoard)
        val adaptedPoint = adaptOmokPoint(point)
        return FourFourRule.validate(adapted, adaptedPoint) || ThreeThreeRule.validate(adapted, adaptedPoint)
    }

    private fun validateWinner(point: OmokPoint): Boolean {
        val adapted = adaptOmokBoard(omokBoard)
        val adaptedPoint = adaptOmokPoint(point)
        return BlackWinRule.validate(adapted, adaptedPoint)
    }

    private fun adaptOmokBoard(omokBoard: OmokBoard): List<List<Int>> {
        val adapted = MutableList(15) { MutableList(15) { 0 } }
        omokBoard.keys.forEach {
            adapted[it.y.value - 1][it.x.value - 1] = when (omokBoard[it]) {
                is BlackStoneState -> 1
                is WhiteStoneState -> 2
                else -> 0
            }
        }
        return adapted
    }

    private fun adaptOmokPoint(point: OmokPoint): Pair<Int, Int> {
        return Pair(point.x.value - 1, point.y.value - 1)
    }
}
