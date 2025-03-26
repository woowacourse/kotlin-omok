package woowacourse.omok.model.rule.count

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.BoardSize
import woowacourse.omok.model.board.Point
import woowacourse.omok.model.board.StoneColor

abstract class OmokCountRule {
    fun calculate(
        board: Board,
        previousPoint: Point,
    ): Boolean = DIRECTIONS.any { isCheckCondition(board, previousPoint, it) }

    abstract fun isCheckCondition(
        board: Board,
        previousPoint: Point,
        dir: Pair<Int, Int>,
    ): Boolean

    fun checkDirection(
        board: Board,
        point: Point,
        direction: Pair<Int, Int>,
    ): Int = countDirection(board, point, direction, STEP) + countDirection(board, point, direction, -STEP) + STEP

    private fun countDirection(
        board: Board,
        point: Point,
        dir: Pair<Int, Int>,
        step: Int,
    ): Int {
        var (x, y) = point.run { x + dir.first * step to y + dir.second * step }

        var count = INITIAL_COUNT
        val targetState = board.findStoneColor(point)
        val targetColor = if (targetState == StoneColor.NONE) StoneColor.BLACK else targetState

        while (isInRange(board, x, y) && board.findStoneColor(Point(x, y)) == targetColor) {
            count++
            x += dir.first * step
            y += dir.second * step
        }

        return count
    }

    private fun isInRange(
        board: Board,
        x: Int,
        y: Int,
    ): Boolean = listOf(x, y).all { it in (BoardSize.MIN_SIZE..board.size) }

    companion object {
        private val DIRECTIONS = listOf(1 to 0, 0 to 1, 1 to 1, -1 to 1)

        @JvmStatic
        protected val OMOK_COUNT = 5

        private const val INITIAL_COUNT = 0
        private const val STEP = 1
    }
}
