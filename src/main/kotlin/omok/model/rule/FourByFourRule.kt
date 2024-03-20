package omok.model.rule

import omok.model.Board
import omok.model.entity.Point
import omok.model.entity.Stone

object FourByFourRule : Rule {
    override fun check(board: Board): Boolean {
        val directions =
            listOf(
                1 to 0,
                0 to 1,
                1 to 1,
                -1 to 1,
            )
        val previousStone = board.previousStone() ?: throw IllegalStateException()

        val sum =
            directions.count { direction ->
                val (vecX, vecY) = direction
                val oppositeDirection = -vecX to -vecY
                (0..3).any {
                    val left = stoneCount(oppositeDirection, previousStone, board, 0, it)
                    val right = stoneCount(direction, previousStone, board, 0, 3 - it)
                    val withBlank =
                        (0..1).any { targetBlankCount ->
                            val leftWithBlank =
                                stoneCountWithBlank(oppositeDirection, previousStone, board, 0, it, 0, targetBlankCount)
                            val rightWithBlank =
                                stoneCountWithBlank(direction, previousStone, board, 0, 3 - it, 0, targetBlankCount)
                            stoneCountWithBlank(direction, previousStone, board, 0, 3 - it, 0, targetBlankCount)
                            leftWithBlank && rightWithBlank
                        }
                    left && right || withBlank
                }
            }
        return sum >= 2
    }

    private tailrec fun stoneCount(
        direction: Pair<Int, Int>,
        stone: Stone,
        board: Board,
        omokCount: Int,
        targetOmokCount: Int,
    ): Boolean {
        val (vecX, vecY) = direction
        val point = stone.point
        val color = stone.stoneColor
        val newPoint = Point(point.x + vecX, point.y + vecY)
        val newStone = Stone(newPoint, color)
        if (targetOmokCount == 0) {
            return !board.stones.contains(newStone)
        }
        if (board.stones.contains(stone).not()) return false
        if (omokCount == targetOmokCount) {
            return !board.stones.contains(newStone)
        }
        return stoneCount(direction, newStone, board, omokCount + 1, targetOmokCount)
    }

    private tailrec fun stoneCountWithBlank(
        direction: Pair<Int, Int>,
        stone: Stone,
        board: Board,
        omokCount: Int,
        targetOmokCount: Int,
        blankCount: Int,
        targetBlankCount: Int,
    ): Boolean {
        val (vecX, vecY) = direction
        val point = stone.point
        val newPoint = Point(point.x + vecX, point.y + vecY)
        val newStone = Stone(newPoint, stone.stoneColor)
        if (targetOmokCount == 0) {
            return !board.stones.contains(newStone)
        }
        if (board.stones.contains(stone).not()) {
            if (blankCount == 1) {
                return false
            }
            return stoneCountWithBlank(
                direction,
                newStone,
                board,
                omokCount,
                targetOmokCount,
                blankCount + 1,
                targetBlankCount,
            )
        }
        if (omokCount == targetOmokCount) {
            return !board.stones.contains(newStone)
        }

        return stoneCountWithBlank(
            direction,
            newStone,
            board,
            omokCount + 1,
            targetOmokCount,
            blankCount,
            targetBlankCount,
        )
    }
}
