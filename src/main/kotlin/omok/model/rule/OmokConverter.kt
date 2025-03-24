package omok.model.rule

import omok.model.board.Board
import omok.model.board.Point
import omok.model.board.StoneColor
import omok.model.rule.lib.ForbiddenMoveRule

object OmokConverter {
    fun converteOmokBoard(board: Board): List<List<Int>> {
        val adapted = MutableList(board.size) { MutableList(board.size) { ForbiddenMoveRule.EMPTY_STONE } }

        board.points.forEach { point ->
            adapted[point.key.y - 1][point.key.x - 1] =
                when (point.value) {
                    StoneColor.BLACK -> ForbiddenMoveRule.BLACK_STONE
                    StoneColor.WHITE -> ForbiddenMoveRule.WHITE_STONE
                    else -> ForbiddenMoveRule.EMPTY_STONE
                }
        }
        return adapted
    }

    fun converteOmokPoint(point: Point): Pair<Int, Int> {
        return Pair(point.x - 1, point.y - 1)
    }
}
