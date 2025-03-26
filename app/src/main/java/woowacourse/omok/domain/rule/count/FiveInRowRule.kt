package woowacourse.omok.domain.rule.count

import woowacourse.omok.domain.board.Board
import woowacourse.omok.domain.board.Point

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
