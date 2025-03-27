package woowacourse.omok.model.rule

import woowacourse.omok.model.Board
import woowacourse.omok.model.MoveResult
import woowacourse.omok.model.Stone

interface Rule {
    fun checkForbiddenMove(
        board: Board,
        newStone: Stone,
    ): MoveResult

    fun checkWinCondition(
        board: Board,
        newStone: Stone,
    ): MoveResult
}
