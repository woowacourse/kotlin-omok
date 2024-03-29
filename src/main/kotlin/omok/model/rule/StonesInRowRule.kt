package omok.model.rule

import omok.model.Board
import omok.model.rule.Rule.Companion.directions

class StonesInRowRule(private val maxStoneCount: Int) : Rule {
    override fun check(board: Board): Boolean {
        return directions.any { direction ->
            checkDirection(board, direction)
        }
    }

    private fun checkDirection(
        board: Board,
        direction: Direction,
    ): Boolean {
        val centerStone = board.previousStone() ?: throw IllegalStateException()
        return (0..<maxStoneCount).any {
            val left = isLineWithoutBlank(board, -direction, centerStone, it)
            val right = isLineWithoutBlank(board, direction, centerStone, maxStoneCount - 1 - it)
            left && right
        }
    }
}
