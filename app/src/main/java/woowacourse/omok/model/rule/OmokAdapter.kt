package woowacourse.omok.model.rule

import woowacourse.omok.model.board.OmokBoard
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.PositionState

object OmokAdapter {
    fun adaptOmokBoard(omokBoard: OmokBoard): List<List<Int>> {
        val adapted = MutableList(omokBoard.ySize) { MutableList(omokBoard.xSize) { 0 } }
        omokBoard.keys.forEach {
            adapted[it.y.point - 1][it.x.point - 1] =
                when (omokBoard.boardState(it)) {
                    PositionState.BLACK_POSITION -> 1
                    PositionState.WHITE_POSITION -> 2
                    else -> 0
                }
        }
        return adapted
    }

    fun adaptOmokPoint(point: Position): Pair<Int, Int> = Pair(point.x.point - 1, point.y.point - 1)
}
