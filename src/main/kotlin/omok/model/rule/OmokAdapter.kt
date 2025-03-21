package omok.model.rule

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.stone.StoneState

object OmokAdapter {
    fun adaptOmokBoard(omokBoard: OmokBoard): List<List<Int>> {
        val adapted = MutableList(omokBoard.ySize) { MutableList(omokBoard.xSize) { 0 } }
        omokBoard.keys.forEach {
            adapted[it.y.value - 1][it.x.value - 1] =
                when (omokBoard.boardState(it)) {
                    StoneState.BLACK -> 1
                    StoneState.WHITE -> 2
                    else -> 0
                }
        }
        return adapted
    }

    fun adaptOmokPoint(value: Position): Pair<Int, Int> = Pair(value.x.value - 1, value.y.value - 1)
}
