package woowacourse.omok.model.rule

import woowacourse.omok.adapter.RuleAdapter
import woowacourse.omok.model.Board
import woowacourse.omok.model.MoveResult
import woowacourse.omok.model.Stone

class RenjuRule : Rule {
    private val rule = RuleAdapter()

    override fun checkForbiddenMove(
        board: Board,
        newStone: Stone,
    ): MoveResult {
        return rule.checkForbiddenMove(board, newStone)
    }

    override fun checkWinCondition(
        board: Board,
        newStone: Stone,
    ): MoveResult {
        return rule.checkWinCondition(board, newStone)
    }
}
