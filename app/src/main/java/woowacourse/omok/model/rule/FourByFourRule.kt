package omok.model.rule

import omok.model.Board
import omok.model.entity.Stone
import omok.model.rule.Rule.Companion.directions

object FourByFourRule : Rule {
    override fun check(
        stone: Stone,
        board: Board,
    ): Boolean {
        val fourCount =
            directions.count { direction ->
                isFour(stone, board, direction)
            }
        return fourCount >= 2
    }

    private fun isFour(
        centerStone: Stone,
        board: Board,
        direction: Direction,
    ): Boolean {
        return (0..3).any {
            val leftWithBlank =
                isLineWithBlank(board, -direction, centerStone, it + 1)
            val left =
                isLineWithoutBlank(board, -direction, centerStone, it)
            val rightWithBlank =
                isLineWithBlank(board, direction, centerStone, 4 - it)
            val right =
                isLineWithoutBlank(board, direction, centerStone, 3 - it)
            leftWithBlank && right || left && rightWithBlank
        }
    }
}
