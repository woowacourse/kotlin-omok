package omok.model.rule

import omok.model.board.Board
import omok.model.board.Point
import omok.model.board.PointState
import omok.model.rule.lib.ForbiddenMoveRule

object OmokAdapter {
    fun adaptOmokBoard(board: Board): List<List<Int>> {
        val adapted = MutableList(board.size) { MutableList(board.size) { ForbiddenMoveRule.EMPTY_STONE } }

        board.points.forEach { point ->
            adapted[point.key.y - 1][point.key.x - 1] =
                when (point.value) {
                    PointState.BLACK -> ForbiddenMoveRule.BLACK_STONE
                    PointState.WHITE -> ForbiddenMoveRule.WHITE_STONE
                    else -> ForbiddenMoveRule.EMPTY_STONE
                }
        }
        return adapted
    }

    fun adaptOmokPoint(point: Point): Pair<Int, Int> {
        return Pair(point.x - 1, point.y - 1)
    }
}
