package woowacourse.omok.model.rule.ban

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Position

interface ForbiddenPlace {
    fun availablePosition(
        board: Board,
        position: Position,
    ): Boolean
}
