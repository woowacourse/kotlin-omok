package omok.model.rule.count

import omok.model.StoneColor
import omok.model.board.Board
import omok.model.board.Point
import omok.model.board.PointState
import omok.model.board.Position

abstract class OmokCountRule {
    fun calculate(
        board: Board,
        previousPoint: Point,
    ): Boolean {
        return DIRECTIONS.any { isCheckCondition(board, previousPoint, it) }
    }

    abstract fun isCheckCondition(
        board: Board,
        previousPoint: Point,
        dir: Pair<Int, Int>,
    ): Boolean

    fun checkDirection(
        board: Board,
        point: Point,
        direction: Pair<Int, Int>,
    ): Int {
        return countDirection(board, point, direction, STEP) + countDirection(board, point, direction, -STEP) + STEP
    }

    private fun countDirection(
        board: Board,
        point: Point,
        dir: Pair<Int, Int>,
        step: Int,
    ): Int {
        var (x, y) = point.position.run { x + dir.first * step to y + dir.second * step }

        var count = INITIAL_COUNT
        val targetColor = point.state.toStoneColor() ?: StoneColor.BLACK

        while (isInRange(x, y) && board.findPoint(Position(x, y))?.state?.toStoneColor() == targetColor) {
            count++
            x += dir.first * step
            y += dir.second * step
        }

        return count
    }

    private fun isInRange(
        x: Int,
        y: Int,
    ): Boolean {
        return listOf(x, y).all { it in (Board.BOARD_MIN_SIZE..Board.BOARD_MAX_SIZE) }
    }

    private fun PointState.toStoneColor(): StoneColor? =
        when (this) {
            PointState.BLACK -> StoneColor.BLACK
            PointState.WHITE -> StoneColor.WHITE
            else -> null
        }

    companion object {
        private val DIRECTIONS = listOf(1 to 0, 0 to 1, 1 to 1, -1 to 1)

        @JvmStatic
        protected val OMOK_COUNT = 5

        private const val INITIAL_COUNT = 0
        private const val STEP = 1
    }
}
