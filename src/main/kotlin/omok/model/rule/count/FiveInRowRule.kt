package omok.model.rule.count

import omok.model.board.Board
import omok.model.board.Point

object FiveInRowRule : OmokCountRule() {
    override fun isCheckCondition(
        board: Board,
        previousPoint: Point,
        dir: Pair<Int, Int>,
    ): Boolean {
        val total = checkDirection(board, previousPoint, dir)
        println(total)
        return total == OMOK_COUNT
    }
}
