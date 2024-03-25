package omok.model.rule.ban

import omok.model.Board
import omok.model.Position
import omok.model.rule.RuleAdapter
import omok.model.rule.winning.ContinualStones

class OverlineForbiddenPlace : ForbiddenPlace {
    override fun availablePosition(
        board: Board,
        position: Position,
    ): Boolean {
        return RuleAdapter.abideOverLineRule(board, position)
    }
}

class OverlineForbiddenPlace2(private val continualStonesStandard: Int = 5) : ForbiddenPlace {
    override fun availablePosition(
        board: Board,
        position: Position,
    ): Boolean = ContinualStones.count(board, position) <= continualStonesStandard
}
