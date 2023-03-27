package omok.domain.util

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.omokRule.RulePosition
import omok.domain.state.BlackStoneState
import omok.domain.state.WhiteStoneState

object OmokAdapter {
    fun adaptOmokBoard(omokBoard: OmokBoard): List<List<Int>> {
        val adapted = MutableList(omokBoard.ySize) { MutableList(omokBoard.xSize) { 0 } }
        omokBoard.keys.forEach {
            adapted[it.y.value - 1][it.x.value - 1] = when (omokBoard[it]) {
                is BlackStoneState -> 1
                is WhiteStoneState -> 2
                else -> 0
            }
        }
        return adapted
    }

    fun adaptOmokPoint(point: OmokPoint): RulePosition {
        return RulePosition(point.x.value - 1, point.y.value - 1)
    }
}
