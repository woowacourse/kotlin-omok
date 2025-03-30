package woowacourse.omok.model.rule

import woowacourse.omok.adapter.RuleAdapter
import woowacourse.omok.model.Board
import woowacourse.omok.model.MoveResult
import woowacourse.omok.model.Stone

class RenjuRule : Rule {
    private val rule = RuleAdapter()

    override fun checkMove(
        board: Board,
        newStone: Stone,
    ): MoveResult {
        return rule.checkMove(board, newStone)
    }
}
