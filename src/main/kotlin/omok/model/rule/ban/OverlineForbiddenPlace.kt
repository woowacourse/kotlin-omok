package omok.model.rule.ban

import omok.model.Board
import omok.model.Position
import omok.model.Stone
import omok.model.rule.ContinualStonesStandard
import omok.model.rule.RuleAdapter
import omok.model.rule.winning.ContinualStones

class OverlineForbiddenPlace(val continualStonesStandard: ContinualStonesStandard = ContinualStonesStandard(5)) :
    ForbiddenPlace {
    override fun availablePosition(
        board: Board,
        position: Position,
    ): Boolean = ContinualStones.count(board, position) < continualStonesStandard.count
}

class OverlineForbiddenPlace2(private val stone: Stone) : ForbiddenPlace {
    lateinit var continualStonesStandard: ContinualStonesStandard

    override fun availablePosition(
        board: Board,
        position: Position,
    ): Boolean = RuleAdapter.abideOverLineRule(stone, board, position)
}
