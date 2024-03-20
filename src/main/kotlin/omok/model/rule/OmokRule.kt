package omok.model.rule

import omok.model.Board
import omok.model.entity.Point
import omok.model.entity.StoneColor

object OmokRule : Rule {
    override fun check(
        board: Board,
        color: StoneColor,
    ): Boolean {
        val directions =
            listOf(
                1 to 0,
                0 to 1,
                1 to 1,
                -1 to 1,
            )
        val previousPoint = board.previousPoint() ?: throw IllegalStateException()
        return directions.any { direction ->
            val (vecX, vecY) = direction
            (0..4).any {
                val left = checkStoneCountOnDirection(-vecX, -vecY, previousPoint, board, color, 0, it)
                val right = checkStoneCountOnDirection(vecX, vecY, previousPoint, board, color, 0, 4 - it)
                left && right
            }
        }
    }

    private tailrec fun checkStoneCountOnDirection(
        vecX: Int,
        vecY: Int,
        point: Point,
        board: Board,
        color: StoneColor,
        omokCount: Int,
        targetOmokCount: Int,
    ): Boolean {
        if (targetOmokCount == 0) return true
        if (board.board.contains(point).not()) return false
        if (board.board[point] != color) return false
        if (omokCount == targetOmokCount) return true

        val newPoint = Point(point.x + vecX, point.y + vecY)
        return checkStoneCountOnDirection(vecX, vecY, newPoint, board, color, omokCount + 1, targetOmokCount)
    }
}
