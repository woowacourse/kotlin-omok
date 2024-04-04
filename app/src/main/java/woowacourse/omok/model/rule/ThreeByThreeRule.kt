package woowacourse.omok.model.rule

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.entity.Point
import woowacourse.omok.model.entity.Stone
import woowacourse.omok.model.entity.StoneColor

object ThreeByThreeRule : Rule {
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
                (0..2).any {
                    val left = stoneCount(oppositeDirection, previousStone, board, 0, it)
                    val right = stoneCount(direction, previousStone, board, 0, 2 - it)
                    val withBlank =
                        (0..1).any { targetBlankCount ->
                            val leftWithBlank =
                                stoneCountWithBlank(oppositeDirection, previousStone, board, 0, it, 0, targetBlankCount)
                            val rightWithBlank =
                                stoneCountWithBlank(direction, previousStone, board, 0, 2 - it, 0, targetBlankCount)
                            stoneCountWithBlank(direction, previousStone, board, 0, 2 - it, 0, targetBlankCount)
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

    fun isInBoard(point: Point) = point.x in 1..15 && point.y in 1..15
}
