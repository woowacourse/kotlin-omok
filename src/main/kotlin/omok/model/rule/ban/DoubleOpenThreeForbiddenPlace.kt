package omok.model.rule.ban

import omok.model.Board
import omok.model.Position
import omok.model.Stone
import omok.model.rule.RuleAdapter

class DoubleOpenThreeForbiddenPlace(private val stone: Stone) : ForbiddenPlace {
    override fun availablePosition(
        board: Board,
        position: Position,
    ): Boolean {
        return RuleAdapter.abideDoubleOpenThreeRule(stone, board, position)
    }
}
