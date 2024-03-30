package woowacourse.omok.model.rule.ban

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.rule.RuleAdapter
import woowacourse.omok.model.rule.library.ThreeThreeRule

class DoubleOpenThreeForbiddenPlace : ForbiddenPlace {
    override fun availablePosition(
        board: Board,
        position: Position,
    ): Boolean {
        return RuleAdapter.abideRule(ThreeThreeRule(board.size), board, position)
    }
}
