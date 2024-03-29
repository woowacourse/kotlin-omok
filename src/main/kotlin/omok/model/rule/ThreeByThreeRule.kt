package omok.model.rule

import omok.model.Board
import omok.model.rule.Rule.Companion.directions

object ThreeByThreeRule : Rule {
    override fun check(board: Board): Boolean {
        val threeCount =
            directions.count {
                isThree(board, it)
            }
        return threeCount >= 2
    }

    private fun isThree(
        board: Board,
        direction: Direction,
    ): Boolean {
        val centerStone = board.previousStone() ?: throw IllegalStateException()
        return (0..2).any {
            val leftWithBlank =
                isLineWithBlankWithPadding(board, -direction, centerStone, it + 1)
            val left =
                isLineWithoutBlankWithPadding(board, -direction, centerStone, it)
            val rightWithBlank =
                isLineWithBlankWithPadding(board, direction, centerStone, 3 - it)
            val right =
                isLineWithoutBlankWithPadding(board, direction, centerStone, 2 - it)
            leftWithBlank && right || left && rightWithBlank
        }
    }
}
