package omok.model.rule.ban

import omok.model.Board
import omok.model.Position
import omok.model.rule.winning.ContinualStones

class OverlineForbiddenPlace(private val continualStonesStandard: Int = 5) : ForbiddenPlace {
    override fun availablePosition(
        board: Board,
        position: Position,
    ): Boolean = ContinualStones.count(board, position) <= continualStonesStandard
}
