package woowacourse.omok.domain.model.rule.winning

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Position

interface WinningCondition {
    fun isWin(
        board: Board,
        position: Position,
    ): Boolean
}
