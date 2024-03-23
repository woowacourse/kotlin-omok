package omok.model.rule.ban

import omok.model.Board
import omok.model.Position
import omok.model.rule.RuleAdapter
import omok.model.rule.library.ThreeThreeRule

class DoubleOpenThreeForbiddenPlace : ForbiddenPlace {
    override fun availablePosition(
        board: Board,
        position: Position,
    ): Boolean {
        return RuleAdapter.abideRule(ThreeThreeRule(board.size), board, position)
    }
}
