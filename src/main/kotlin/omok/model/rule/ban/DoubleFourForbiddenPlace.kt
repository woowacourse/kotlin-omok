package omok.model.rule.ban

import omok.model.Board
import omok.model.Position
import omok.model.rule.RuleAdapter
import omok.model.rule.library.FourFourRule

class DoubleFourForbiddenPlace : ForbiddenPlace {
    override fun availablePosition(
        board: Board,
        position: Position,
    ): Boolean {
        return RuleAdapter.abideRule(FourFourRule, board, position)
    }
}
