package omok.model.rule

import omok.model.board.Board
import omok.model.board.Point
import omok.model.rule.Rule.Companion.toStoneColor
import omok.model.stone.Position

class FiveInRow : Rule {
    override fun calculate(
        board: Board,
        previousPoint: Point,
    ): Boolean {
        return DIRECTIONS.any { checkDirection(board, previousPoint, it) }
    }

    private fun checkDirection(
        board: Board,
        point: Point,
        direction: Pair<Int, Int>,
    ): Boolean {
        val total = countDirection(board, point, direction, STEP) + countDirection(board, point, direction, -STEP) + STEP
        return total >= OMOK_COUNT
    }

    private fun countDirection(
        board: Board,
        point: Point,
        dir: Pair<Int, Int>,
        step: Int,
    ): Int {
        var (x, y) = point.position.run { x + dir.first * step to y + dir.second * step }

        var count = INITIAL_COUNT
        val targetColor = point.state.toStoneColor() ?: return INITIAL_COUNT

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

    companion object {
        private val DIRECTIONS = listOf(1 to 0, 0 to 1, 1 to 1, -1 to 1)
        private const val OMOK_COUNT = 5
        private const val STEP = 1
        private const val INITIAL_COUNT = 0
    }
}
