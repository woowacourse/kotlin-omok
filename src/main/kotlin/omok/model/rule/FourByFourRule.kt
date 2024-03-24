package omok.model.rule

import omok.model.Board
import omok.model.entity.Stone

object FourByFourRule : Rule {
    override fun check(board: Board): Boolean {
        val sum =
            directions.count { direction ->
                checkDirection(board, direction)
            }
        return sum >= 2
    }

    private fun checkDirection(
        board: Board,
        direction: Direction,
    ): Boolean {
        val previousStone = board.previousStone() ?: throw IllegalStateException()
        return (0..3).any {
            val left = uniDirectionalScan(board, direction, previousStone, it)
            val right = uniDirectionalScan(board, -direction, previousStone, 3 - it)
            left && right
        }
    }

    private fun uniDirectionalScan(
        board: Board,
        direction: Direction,
        stone: Stone,
        length: Int,
    ): Boolean =
        isLineWithoutBlank(board, direction, stone, length) ||
            isLineWithBlank(board, direction, stone, length + 1)
}
