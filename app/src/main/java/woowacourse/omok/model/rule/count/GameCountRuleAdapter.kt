package woowacourse.omok.model.rule.count

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Point
import woowacourse.omok.model.rule.GameRule

class GameCountRuleAdapter(
    private val omokCountRule: OmokCountRule,
) : GameRule {
    override fun validateMove(
        board: Board,
        validationPoint: Point,
    ): Boolean = omokCountRule.calculate(board, validationPoint)
}
