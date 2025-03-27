package woowacourse.omok.model.rule

import woowacourse.omok.model.board.OmokBoard
import woowacourse.omok.model.board.OmokBoardConfig.BLACK_STONE
import woowacourse.omok.model.board.OmokBoardConfig.EMPTY_STONE
import woowacourse.omok.model.board.OmokBoardConfig.WHITE_STONE
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.PositionState

object OmokAdapter {
    fun adaptOmokBoard(omokBoard: OmokBoard): List<List<Int>> {
        val adapted = MutableList(omokBoard.ySize) { MutableList(omokBoard.xSize) { 0 } }
        omokBoard.keys.forEach {
            adapted[it.y.point - 1][it.x.point - 1] =
                when (omokBoard.boardState(it)) {
                    PositionState.BLACK_POSITION -> BLACK_STONE
                    PositionState.WHITE_POSITION -> WHITE_STONE
                    else -> EMPTY_STONE
                }
        }
        return adapted
    }

    fun adaptOmokPoint(point: Position): Pair<Int, Int> = Pair(point.x.point - 1, point.y.point - 1)
}
