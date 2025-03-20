package omok.model.rule

import omok.model.board.Board
import omok.model.board.Point
import omok.model.board.PointState
import omok.model.rule.lib.ForbiddenMoveRule

object OmokAdapter {
    fun adaptOmokBoard(board: Board): List<List<Int>> {
        val adapted = MutableList(Board.BOARD_MAX_SIZE) { MutableList(Board.BOARD_MAX_SIZE) { ForbiddenMoveRule.EMPTY_STONE } }

        board.points.forEach { point ->
            adapted[point.position.y - 1][point.position.x - 1] =
                when (board.findPoint(point.position)?.state) {
                    PointState.BLACK -> ForbiddenMoveRule.BLACK_STONE
                    PointState.WHITE -> ForbiddenMoveRule.WHITE_STONE
                    else -> ForbiddenMoveRule.EMPTY_STONE
                }
        }
        return adapted
    }

    fun adaptOmokPoint(point: Point): Pair<Int, Int> {
        return Pair(point.position.x - 1, point.position.y - 1)
    }
}
