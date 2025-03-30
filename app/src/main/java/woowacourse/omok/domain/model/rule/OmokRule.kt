package woowacourse.omok.domain.model.rule

import woowacourse.omok.domain.model.Board.Companion.DEFAULT_BOARD_SIZE
import woowacourse.omok.domain.model.Direction
import woowacourse.omok.domain.model.Direction.DIAGONAL_DOWN
import woowacourse.omok.domain.model.Direction.DIAGONAL_UP
import woowacourse.omok.domain.model.Direction.HORIZONTAL
import woowacourse.omok.domain.model.Direction.VERTICAL
import woowacourse.omok.domain.model.Point

class OmokRule(private val size: Int = DEFAULT_BOARD_SIZE) {
    fun isOmok(
        lastPoint: Point,
        points: Set<Point>,
    ): Boolean =
        DIRECTIONS.any {
            isSerialOmok(lastPoint, it, points)
        }

    private fun isSerialOmok(
        lastPoint: Point,
        directions: Direction,
        points: Set<Point>,
    ): Boolean = directions.direction.sumOf { countConnected(lastPoint, it, points) } >= OMOK_STONE_COUNT - 1

    private fun countConnected(
        point: Point,
        direction: Pair<Int, Int>,
        points: Set<Point>,
    ): Int {
        val boardRange = 0..<size
        var count = 0

        val (dx, dy) = direction
        var (x, y) = point.x + dx to point.y + dy
        while (x in boardRange && y in boardRange && Point(x, y) in points) {
            count++
            x += dx
            y += dy
        }
        return count
    }

    companion object {
        private const val OMOK_STONE_COUNT = 5
        private val DIRECTIONS = listOf(HORIZONTAL, VERTICAL, DIAGONAL_UP, DIAGONAL_DOWN)
    }
}
