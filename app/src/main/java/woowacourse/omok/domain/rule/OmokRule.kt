package woowacourse.omok.domain.rule

import woowacourse.omok.domain.OmokBoard
import woowacourse.omok.domain.Point
import woowacourse.omok.domain.stone.OmokStones
import woowacourse.omok.domain.stone.Stone

class OmokRule(
    private val boardSize: Int = OmokBoard.DEFAULT_BOARD_SIZE,
    private val forbiddenMoveRule: ForbiddenMoveRule = RenjuRule(boardSize),
) {
    fun checkViolation(
        stones: OmokStones,
        lastStone: Stone,
    ): Violation = forbiddenMoveRule.checkViolation(stones, lastStone)

    fun isOmok(
        stones: OmokStones,
        lastStone: Stone,
    ): Boolean =
        Direction.directionPairs.any { directions ->
            isSerialOmok(
                stones.stones.filter { it.color == lastStone.color }.map { it.point },
                lastStone.point,
                directions,
            )
        }

    private fun isSerialOmok(
        points: List<Point>,
        lastPoint: Point,
        directions: Pair<Direction, Direction>,
    ): Boolean {
        val forwardCount = countConnected(points, lastPoint, directions.first)
        val backwardCount = countConnected(points, lastPoint, directions.second)
        return forwardCount + backwardCount >= OMOK_STONE_COUNT - 1
    }

    private fun countConnected(
        points: List<Point>,
        point: Point,
        direction: Direction,
    ): Int {
        val boardRange = 0 until boardSize
        var count = 0

        val (dx, dy) = direction.x to direction.y
        var (x, y) = point.row + dx to point.col + dy
        while (x in boardRange && y in boardRange && Point(x, y) in points) {
            count++
            x += dx
            y += dy
        }
        return count
    }

    companion object {
        private const val OMOK_STONE_COUNT = 5
    }
}
