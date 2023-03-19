package omok.domain.adapter

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.state.BlackStoneState
import omok.domain.state.WhiteStoneState

object OmokAdapter {
    fun adaptBoard(omokBoard: OmokBoard): List<List<Int>> {
        val ySize = omokBoard.keys.map { it.y.value }.distinct().size
        val xSize = omokBoard.keys.map { it.x.value }.distinct().size
        val board = MutableList(ySize) { MutableList(xSize) { 0 } }

        omokBoard.keys.forEach {
            board[it.y.value - 1][it.x.value - 1] = when (omokBoard[it]) {
                BlackStoneState -> 1
                WhiteStoneState -> 2
                else -> 0
            }
        }
        return board
    }

    fun adaptPoint(omokPoint: OmokPoint): Pair<Int, Int> {
        return Pair(omokPoint.x.value - 1, omokPoint.y.value - 1)
    }
}
