package woowacourse.omok.domain.rule

import woowacourse.omok.domain.board.Board
import woowacourse.omok.domain.board.Point

interface GameRule {
    fun validateMove(
        board: Board,
        validationPoint: Point,
    ): Boolean
}
