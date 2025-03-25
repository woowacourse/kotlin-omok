package omok.model.rule.count

import omok.model.board.Board
import omok.model.board.Point
import omok.model.rule.GameRule

class GameCountRuleAdapter(
    val omokCountRule: OmokCountRule,
) : GameRule {
    override fun validateMove(
        board: Board,
        previousPoint: Point,
    ): Boolean {
        return omokCountRule.calculate(board, previousPoint)
    }
}
