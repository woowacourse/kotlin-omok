package woowacourse.omok.domain.rule.count

import woowacourse.omok.domain.board.Board
import woowacourse.omok.domain.board.Point
import woowacourse.omok.domain.rule.GameRule

class GameCountRuleAdapter(
    private val omokCountRule: OmokCountRule,
) : GameRule {
    override fun validateMove(
        board: Board,
        validationPoint: Point,
    ): Boolean = omokCountRule.calculate(board, validationPoint)
}
