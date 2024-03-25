package omok.model.rule.ban

import omok.model.Board
import omok.model.Position
import omok.model.rule.RuleAdapter

class DoubleFourForbiddenPlace : ForbiddenPlace {
    override fun availablePosition(
        board: Board,
        position: Position,
    ): Boolean {
        return RuleAdapter.abideDoubleFourRule(board, position)
    }
}
