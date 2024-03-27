package omok.model.rule.ban

import omok.model.board.Board
import omok.model.board.Position
import omok.model.rule.RuleAdapter
import omok.model.rule.library.OverlineRule

class OverlineForbiddenPlace : ForbiddenPlace {
    override fun availablePosition(
        board: Board,
        position: Position,
    ): Boolean {
        return RuleAdapter.abideRule(OverlineRule(board.size), board, position)
    }
}
