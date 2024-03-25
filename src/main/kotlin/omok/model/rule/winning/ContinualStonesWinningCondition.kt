package omok.model.rule.winning

import omok.model.Board
import omok.model.ContinualStonesCondition
import omok.model.Position

class ContinualStonesWinningCondition(
    private val continualStonesStandard: Int,
    private val continualStonesCondition: ContinualStonesCondition,
) : WinningCondition {
    override fun isWin(
        board: Board,
        position: Position,
    ): Boolean = continualStonesCondition.map(ContinualStones.count(board, position), continualStonesStandard)
}
