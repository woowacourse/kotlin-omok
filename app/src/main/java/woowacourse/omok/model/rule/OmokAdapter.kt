package omok.model.rule

import omok.model.rule.lib.ForbiddenMoveRule
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Point
import woowacourse.omok.model.board.PointState

object OmokAdapter {
    fun adaptOmokBoard(board: Board): List<List<Int>> {
        val adapted = MutableList(board.size) { MutableList(board.size) { ForbiddenMoveRule.EMPTY_STONE } }

        board.points.forEach { point ->
            adapted[point.y - 1][point.x - 1] =
                when (board.findPoint(point).state) {
                    PointState.BLACK -> ForbiddenMoveRule.BLACK_STONE
                    PointState.WHITE -> ForbiddenMoveRule.WHITE_STONE
                    else -> ForbiddenMoveRule.EMPTY_STONE
                }
        }
        return adapted
    }

    fun adaptOmokPoint(point: Point): Pair<Int, Int> = Pair(point.x - 1, point.y - 1)
}
