package omok.model.rule.count

import omok.model.board.Board
import omok.model.board.Point
import omok.model.rule.OmokRule

class OmokCountRuleAdapter(
    val omokCountRule: OmokCountRule,
) : OmokRule {
    override fun validateMove(
        board: Board,
        previousPoint: Point,
    ): Boolean {
        return omokCountRule.calculate(board, previousPoint)
    }
}
