package omok.model.rule

import omok.model.Board
import omok.model.entity.Point
import omok.model.entity.StoneColor

class SamSamRule : Rule {
    override fun check(
        board: Board,
        color: StoneColor,
    ): Boolean {
        val directions =
            listOf(
                1 to 0, // 가로
                0 to 1, // 세로
                1 to 1, // 오른쪽 위에서 왼쪽 아래
                -1 to 1, // 왼쪽 위에서 오른쪽 아래
            )
        val previousPoint = board.previousPoint() ?: throw IllegalStateException()
        val sum =
            directions.count { direction ->
                val (vecX, vecY) = direction
                val oppositeDirection = -vecX to -vecY
                (0..2).any {
                    val left = stoneCount(oppositeDirection, previousPoint, board, color, 0, it)
                    val right = stoneCount(direction, previousPoint, board, color, 0, 2 - it)
                    val withBlank =
                        (0..1).any { targetBlankCount ->
                            val leftWithBlank =
                                stoneCountWithBlank(oppositeDirection, previousPoint, board, color, 0, it, 0, targetBlankCount)
                            val rightWithBlank =
                                stoneCountWithBlank(direction, previousPoint, board, color, 0, 2 - it, 0, targetBlankCount)
                            stoneCountWithBlank(direction, previousPoint, board, color, 0, 2 - it, 0, targetBlankCount)
                            leftWithBlank && rightWithBlank
                        }
                    left && right || withBlank
                }
            }
        return sum >= 2
    }

    private tailrec fun stoneCount(
        direction: Pair<Int, Int>,
        point: Point,
        board: Board,
        color: StoneColor,
        omokCount: Int,
        targetOmokCount: Int,
    ): Boolean {
        val (vecX, vecY) = direction
        val newPoint = Point(point.x + vecX, point.y + vecY)
        if (targetOmokCount == 0) {
            return !board.board.contains(newPoint)
        }
        if (board.board.contains(point).not()) return false
        if (board.board[point] != color) return false
        if (omokCount == targetOmokCount) {
            return !board.board.contains(newPoint)
        }
        return stoneCount(direction, newPoint, board, color, omokCount + 1, targetOmokCount)
    }

    private tailrec fun stoneCountWithBlank(
        direction: Pair<Int, Int>,
        point: Point,
        board: Board,
        color: StoneColor,
        omokCount: Int,
        targetOmokCount: Int,
        blankCount: Int,
        targetBlankCount: Int,
    ): Boolean {
        val (vecX, vecY) = direction
        val newPoint = Point(point.x + vecX, point.y + vecY)
        if (targetOmokCount == 0) {
            return !board.board.contains(newPoint)
        }
        if (board.board.contains(point).not()) {
            if (blankCount == 1) {
                return false
            }
            return stoneCountWithBlank(
                direction,
                newPoint,
                board,
                color,
                omokCount,
                targetOmokCount,
                blankCount + 1,
                targetBlankCount,
            )
        }
        if (board.board[point] != color) return false
        if (omokCount == targetOmokCount) {
            return !board.board.contains(newPoint)
        }

        return stoneCountWithBlank(
            direction,
            newPoint,
            board,
            color,
            omokCount + 1,
            targetOmokCount,
            blankCount,
            targetBlankCount,
        )
    }
}
