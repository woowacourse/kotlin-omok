package omok.model.rule.winning

import omok.model.Board
import omok.model.ContinualStonesCondition
import omok.model.Position
import omok.model.rule.ContinualStonesStandard

class ContinualStonesWinningCondition(
    val continualStonesStandard: ContinualStonesStandard,
    private val continualStonesCondition: ContinualStonesCondition,
) : WinningCondition {
    override fun isWin(
        board: Board,
        position: Position,
    ): Boolean = continualStonesCondition.map(ContinualStones.count(board, position), continualStonesStandard)
}
