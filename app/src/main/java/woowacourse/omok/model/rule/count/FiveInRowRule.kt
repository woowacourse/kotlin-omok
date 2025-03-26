package omok.model.rule.count

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Point

class FiveInRowRule : OmokCountRule() {
    override fun isCheckCondition(
        board: Board,
        previousPoint: Point,
        dir: Pair<Int, Int>,
    ): Boolean {
        val total = checkDirection(board, previousPoint, dir)
        return total == OMOK_COUNT
    }
}
