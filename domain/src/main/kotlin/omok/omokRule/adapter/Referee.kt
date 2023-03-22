package omok.omokRule.adapter

import omok.OmokBoard
import omok.OmokPoint
import omok.omokRule.BlackWinRule
import omok.omokRule.FourRule
import omok.omokRule.ThreeRule
import omok.omokRule.WhiteWinRule
import omok.state.BlackStoneState
import omok.state.StoneState
import omok.state.WhiteStoneState

class Referee : RenjuRule, WinRule {
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
        val ySize = omokBoard.value.keys.map { it.y }.distinct().size
        val xSize = omokBoard.value.keys.map { it.x }.distinct().size
        val board = MutableList(ySize) { MutableList(xSize) { 0 } }

        omokBoard.value.keys.forEach {
            board[it.y - 1][it.x - 1] = when (omokBoard[it]) {
                BlackStoneState -> 1
                WhiteStoneState -> 2
                else -> 0
            }
        }
        return board
    }

    private fun adaptPoint(omokPoint: OmokPoint): Pair<Int, Int> {
        return Pair(omokPoint.x - 1, omokPoint.y - 1)
    }
}
