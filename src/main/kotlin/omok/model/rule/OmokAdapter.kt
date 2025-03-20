package omok.model.rule

import omok.model.board.Board
import omok.model.board.Point
import omok.model.board.PointState

object OmokAdapter {
    fun adaptOmokBoard(board: Board): List<List<Int>> {
        val adapted = MutableList(15) { MutableList(15) { 0 } }

        board.points.forEach { point ->
            adapted[point.position.y - 1][point.position.x - 1] =
                when (board.findPoint(point.position)?.state) {
                    PointState.BLACK -> 1
                    PointState.WHITE -> 2
                    else -> 0
                }
        }
        return adapted
    }

    fun adaptOmokPoint(point: Point): Pair<Int, Int> {
        return Pair(point.position.x - 1, point.position.y - 1)
    }
}
