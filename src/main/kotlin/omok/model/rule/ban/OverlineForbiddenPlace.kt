package omok.model.rule.ban

import omok.model.Board
import omok.model.Position
import omok.model.rule.ContinualStonesStandard
import omok.model.rule.RuleAdapter

class OverlineForbiddenPlace : ForbiddenPlace {
    lateinit var continualStonesStandard: ContinualStonesStandard

    override fun availablePosition(
        board: Board,
        position: Position,
    ): Boolean = RuleAdapter.abideOverLineRule(continualStonesStandard, board, position)
}
