package omok.model.rule

import omok.model.Board
import omok.model.entity.Stone

object ThreeByThreeRule : Rule {
    override fun check(board: Board): Boolean {
        val sum =
            directions.count {
                checkDirection(board, it)
            }
        return sum >= 2
    }

    private fun checkDirection(
        board: Board,
        direction: Direction,
    ): Boolean {
        val previousStone = board.previousStone() ?: throw IllegalStateException()
        return (0..2).any {
            val left =
                uniDirectionalScan(board, -direction, previousStone, it)
            val right =
                uniDirectionalScan(board, direction, previousStone, 2 - it)
            left && right
        }
    }

    private fun uniDirectionalScan(
        board: Board,
        direction: Direction,
        stone: Stone,
        length: Int,
    ): Boolean =
        isLineWithoutBlankWithPadding(board, direction, stone, length) ||
            isLineWithBlankWithPadding(board, direction, stone, length + 1)
}
