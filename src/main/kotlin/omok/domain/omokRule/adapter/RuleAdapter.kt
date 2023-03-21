package omok.domain.omokRule.adapter

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.omokRule.BlackWinRule
import omok.domain.omokRule.FourRule
import omok.domain.omokRule.ThreeRule
import omok.domain.omokRule.WhiteWinRule
import omok.domain.state.BlackStoneState
import omok.domain.state.StoneState
import omok.domain.state.WhiteStoneState

class RuleAdapter : Rule {
    override fun isForbidden(omokBoard: OmokBoard, omokPoint: OmokPoint): Boolean {
        val adaptBoard = adaptBoard(omokBoard)
        val adaptPoint = adaptPoint(omokPoint)
        return ThreeRule.validate(adaptBoard, adaptPoint) || FourRule.validate(adaptBoard, adaptPoint)
    }

    override fun isWin(omokBoard: OmokBoard, omokPoint: OmokPoint, stoneState: StoneState): Boolean {
        val adaptBoard = adaptBoard(omokBoard)
        val adaptPoint = adaptPoint(omokPoint)
        return when (stoneState) {
            BlackStoneState -> BlackWinRule.validate(adaptBoard, adaptPoint)
            WhiteStoneState -> WhiteWinRule.validate(adaptBoard, adaptPoint)
            else -> false
        }
    }

    private fun adaptBoard(omokBoard: OmokBoard): List<List<Int>> {
        val ySize = omokBoard.value.keys.map { it.y.value }.distinct().size
        val xSize = omokBoard.value.keys.map { it.x.value }.distinct().size
        val board = MutableList(ySize) { MutableList(xSize) { 0 } }

        omokBoard.value.keys.forEach {
            board[it.y.value - 1][it.x.value - 1] = when (omokBoard[it]) {
                BlackStoneState -> 1
                WhiteStoneState -> 2
                else -> 0
            }
        }
        return board
    }

    private fun adaptPoint(omokPoint: OmokPoint): Pair<Int, Int> {
        return Pair(omokPoint.x.value - 1, omokPoint.y.value - 1)
    }
}
