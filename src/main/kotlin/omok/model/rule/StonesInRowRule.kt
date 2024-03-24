package omok.model.rule

import omok.model.Board

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
        val previousStone = board.previousStone() ?: throw IllegalStateException()
        return (0..<maxStoneCount - 1).any {
            val left = isLineWithoutBlank(board, -direction, previousStone, it)
            val right = isLineWithoutBlank(board, -direction, previousStone, maxStoneCount - it)
            left && right
        }
    }
}
