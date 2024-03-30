package woowacourse.omok.model.rule.ban

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.rule.RuleAdapter
import woowacourse.omok.model.rule.library.FourFourRule

class DoubleFourForbiddenPlace : ForbiddenPlace {
    override fun availablePosition(
        board: Board,
        position: Position,
    ): Boolean {
        return RuleAdapter.abideRule(FourFourRule(board.size), board, position)
    }
}
