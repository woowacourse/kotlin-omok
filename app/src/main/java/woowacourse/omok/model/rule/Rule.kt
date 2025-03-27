package woowacourse.omok.model.rule

import woowacourse.omok.model.Board
import woowacourse.omok.model.Color
import woowacourse.omok.model.MoveResult
import woowacourse.omok.model.position.Position

interface Rule {
    fun checkForbiddenMove(
        board: Board,
        position: Position,
        color: Color,
    ): MoveResult

    fun checkWinCondition(
        board: Board,
        position: Position,
        color: Color,
    ): MoveResult
}
