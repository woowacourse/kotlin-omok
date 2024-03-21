package omok.model.rule

import omok.model.Board
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor

object ThreeByThreeRule : Rule {
    override fun check(board: Board): Boolean =
        board.stones.any {
            checkOneStone(it, board)
        }

    private fun checkOneStone(
        stone: Stone,
        board: Board,
    ): Boolean {
        val directions =
            listOf(
                1 to 0,
                0 to 1,
                1 to 1,
                -1 to 1,
            )
        val sum =
            directions.count { direction ->
                val (vecX, vecY) = direction
                val oppositeDirection = -vecX to -vecY
                (0..2).any {
                    val left = stoneCount(oppositeDirection, stone, board, 0, it)
                    val right = stoneCount(direction, stone, board, 0, 2 - it)
                    val withBlank =
                        (0..1).any { targetBlankCount ->
                            val leftWithBlank =
                                stoneCountWithBlank(oppositeDirection, stone, board, 0, it, 0, targetBlankCount)
                            val rightWithBlank =
                                stoneCountWithBlank(direction, stone, board, 0, 2 - it, 0, targetBlankCount)
                            stoneCountWithBlank(direction, stone, board, 0, 2 - it, 0, targetBlankCount)
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
        val nextPoint = Point(point.x + vecX, point.y + vecY)
        val nextStone = Stone(nextPoint, stone.stoneColor)
        val nextWhiteStone = Stone(nextPoint, StoneColor.WHITE)
        val nextBlackStone = Stone(nextPoint, StoneColor.BLACK)

        val nextNextPoint = Point(point.x + vecX * 2, point.y + vecY * 2)
        val nextNextBlackStone = Stone(nextNextPoint, StoneColor.BLACK)
        val nextNextWhiteStone = Stone(nextNextPoint, StoneColor.WHITE)

        val isNextStoneBlank =
            isInBoard(nextPoint) &&
                !board.stones.contains(nextWhiteStone) &&
                !board.stones.contains(nextBlackStone)
        val isNextNextStoneBlank =
            isInBoard(nextNextPoint) &&
                !board.stones.contains(nextNextBlackStone) &&
                !board.stones.contains(nextNextWhiteStone)

        val checkNotRealThree =
            isNextStoneBlank && isNextNextStoneBlank

        if (board.stones.contains(stone).not()) return false
        if (targetOmokCount == omokCount || targetOmokCount == 0) {
            return checkNotRealThree
        }
        return stoneCount(direction, nextStone, board, omokCount + 1, targetOmokCount)
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
        val nextPoint = Point(point.x + vecX, point.y + vecY)
        val nextStone = Stone(nextPoint, stone.stoneColor)
        val nextWhiteStone = Stone(nextPoint, StoneColor.WHITE)
        val nextBlackStone = Stone(nextPoint, StoneColor.BLACK)

        val nextNextPoint = Point(point.x + vecX * 2, point.y + vecY * 2)
        val nextNextBlackStone = Stone(nextNextPoint, StoneColor.BLACK)
        val nextNextWhiteStone = Stone(nextNextPoint, StoneColor.WHITE)

        val isNextStoneBlank =
            isInBoard(nextPoint) &&
                !board.stones.contains(nextWhiteStone) &&
                !board.stones.contains(nextBlackStone)
        val isNextNextStoneBlank =
            isInBoard(nextNextPoint) &&
                !board.stones.contains(nextNextBlackStone) &&
                !board.stones.contains(nextNextWhiteStone)

        val checkNotRealThree =
            isNextStoneBlank && isNextNextStoneBlank

        if (board.stones.contains(stone).not()) {
            if (blankCount == 1) {
                return false
            }
            return stoneCountWithBlank(
                direction,
                nextStone,
                board,
                omokCount,
                targetOmokCount,
                blankCount + 1,
                targetBlankCount,
            )
        }
        if (targetOmokCount == omokCount || targetOmokCount == 0) {
            return checkNotRealThree
        }

        return stoneCountWithBlank(
            direction,
            nextStone,
            board,
            omokCount + 1,
            targetOmokCount,
            blankCount,
            targetBlankCount,
        )
    }

    private fun isInBoard(point: Point) = point.x in 1..15 && point.y in 1..15
}
