package omok.model.rule

import omok.model.Board
import omok.model.entity.Point
import omok.model.entity.StoneColor

class OmokRule : Rule {
    private val visited: MutableMap<Point, Boolean> = mutableMapOf()

    override fun check(
        board: Board,
        color: StoneColor,
    ): Boolean {
        val lastPoint = board.previousPoint() ?: throw IllegalStateException()
        return checkOmok(lastPoint, color, 1, board)
    }

    private fun checkOmok(
        point: Point,
        color: StoneColor,
        omokCount: Int,
        board: Board,
    ): Boolean {
        if (visited[point] == true) return false
        if (board.board.contains(point).not()) return false
        if (board.board[point] != color) return false
        if (omokCount == 5) return true
        visited[point] = true
        val x = point.x
        val y = point.y
        val targetList =
            listOf(
                Point(x - 1, y - 1),
                Point(x, y - 1),
                Point(x + 1, y - 1),
                Point(x - 1, y),
                Point(x + 1, y),
                Point(x + 1, y + 1),
                Point(x, y + 1),
                Point(x - 1, y + 1),
            )
        return targetList.any {
            checkOmok(it, color, omokCount + 1, board)
        }
    }
}
