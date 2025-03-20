package omok.model.rule

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.stone.StoneState

object OmokAdapter {
    fun adaptOmokBoard(omokBoard: OmokBoard): List<List<Int>> {
        val adapted = MutableList(omokBoard.ySize) { MutableList(omokBoard.xSize) { 0 } }
        omokBoard.keys.forEach {
            adapted[it.y.point - 1][it.x.point - 1] =
                when (omokBoard.boardState(it)) {
                    StoneState.BLACK -> 1
                    StoneState.WHITE -> 2
                    else -> 0
                }
        }
        return adapted
    }

    fun adaptOmokPoint(point: Position): Pair<Int, Int> = Pair(point.x.point - 1, point.y.point - 1)
}
