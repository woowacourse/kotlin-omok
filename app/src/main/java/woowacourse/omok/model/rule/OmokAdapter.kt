package woowacourse.omok.model.rule

import woowacourse.omok.model.board.OmokBoard
import woowacourse.omok.model.board.OmokBoardConfig.INDEX_OFFSET
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.PositionState

object OmokAdapter {
    fun adaptOmokBoard(omokBoard: OmokBoard): List<List<PositionState>> {
        val adapted =
            MutableList(omokBoard.ySize) { MutableList(omokBoard.xSize) { PositionState.NONE } }
        omokBoard.keys.forEach {
            adapted[it.y.point - INDEX_OFFSET][it.x.point - INDEX_OFFSET] = omokBoard.boardState(it)
        }
        return adapted
    }

    fun adaptOmokPoint(point: Position): Pair<Int, Int> = Pair(point.x.point - INDEX_OFFSET, point.y.point - INDEX_OFFSET)
}
