package omok.model.rule

import omok.model.Board
import omok.model.entity.Stone
import omok.model.rule.Rule.Companion.directions

class StonesInRowRule(private val maxStoneCount: Int) : Rule {
    override fun check(
        stone: Stone,
        board: Board,
    ): Boolean {
        return directions.any { direction ->
            checkDirection(stone, board, direction)
        }
    }

    private fun checkDirection(
        centerStone: Stone,
        board: Board,
        direction: Direction,
    ): Boolean {
        return (0..<maxStoneCount).any {
            val left = isLineWithoutBlank(board, -direction, centerStone, it)
            val right = isLineWithoutBlank(board, direction, centerStone, maxStoneCount - 1 - it)
            left && right
        }
    }
}
