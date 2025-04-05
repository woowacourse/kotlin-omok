package omok.model.rule

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Point

interface OmokRule {
    fun calculate(
        board: Board,
        previousPoint: Point,
    ): Boolean
}
