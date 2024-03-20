package omok.model.rule

import omok.model.Board
import omok.model.entity.Point
import omok.model.entity.Stone

object SixMokRule : Rule {
    override fun check(board: Board): Boolean {
        val directions =
            listOf(
                1 to 0,
                0 to 1,
                1 to 1,
                -1 to 1,
            )
        val previousStone = board.previousStone() ?: throw IllegalStateException()
        return directions.any { direction ->
            val (vecX, vecY) = direction
            (0..5).any {
                val left = checkStoneCountOnDirection(-vecX, -vecY, previousStone, board, 0, it)
                val right = checkStoneCountOnDirection(vecX, vecY, previousStone, board, 0, 5 - it)
                left && right
            }
        }
    }

    private tailrec fun checkStoneCountOnDirection(
        vecX: Int,
        vecY: Int,
        stone: Stone,
        board: Board,
        omokCount: Int,
        targetOmokCount: Int,
    ): Boolean {
        if (targetOmokCount == 0) return true
        if (board.stones.contains(stone).not()) return false
        if (omokCount == targetOmokCount) return true

        val point = stone.point
        val newPoint = Point(point.x + vecX, point.y + vecY)
        val newStone = Stone(newPoint, stone.stoneColor)
        return checkStoneCountOnDirection(vecX, vecY, newStone, board, omokCount + 1, targetOmokCount)
    }
}
