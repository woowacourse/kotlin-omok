package omok.model.rule.winning

import omok.model.Board
import omok.model.ContinualStonesCondition
import omok.model.Position
import omok.model.rule.ContinualStonesStandard
import omok.model.rule.ContinualStonesStandard.Companion.MIN_CONTINUAL_STONES_COUNT

class ContinualStonesWinningCondition(
    val continualStonesStandard: ContinualStonesStandard,
    private val continualStonesCondition: ContinualStonesCondition,
) : WinningCondition {
    fun canHaveDoubleRule(): Boolean = continualStonesStandard > ContinualStonesStandard(MIN_CONTINUAL_STONES_COUNT)

    fun canHaveOverlineRule(): Boolean = continualStonesCondition == ContinualStonesCondition.EXACT

    override fun isWin(
        board: Board,
        position: Position,
    ): Boolean = continualStonesCondition.map(ContinualStones.count(board, position), continualStonesStandard)
}
