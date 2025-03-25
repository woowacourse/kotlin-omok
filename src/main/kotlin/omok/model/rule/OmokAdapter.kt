package omok.model.rule

import omok.model.board.Board
import omok.model.board.Position
import omok.model.stone.StoneState

class OmokAdapter {
    fun adaptOmokBoard(omokBoard: Board): List<List<Int>> {
        val adapted = MutableList(omokBoard.ySize) { MutableList(omokBoard.xSize) { 0 } }
        omokBoard.keys.forEach {
            adapted[it.y.value - 1][it.x.value - 1] =
                when (omokBoard.stoneState(it)) {
                    StoneState.BLACK -> 1
                    StoneState.WHITE -> 2
                    else -> 0
                }
        }
        return adapted
    }

    fun adaptOmokPoint(point: Position): RulePosition = RulePosition(point.x.value - 1, point.y.value - 1)
}
