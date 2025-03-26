package woowacourse.omok.model.rule

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Point

interface GameRule {
    fun validateMove(
        board: Board,
        validationPoint: Point,
    ): Boolean
}
