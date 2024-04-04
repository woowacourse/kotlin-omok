package woowacourse.omok.domain.model.rule.winning

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.ContinualStonesCondition
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.rule.ContinualStonesStandard
import woowacourse.omok.domain.model.rule.ContinualStonesStandard.Companion.MIN_CONTINUAL_STONES_COUNT

class ContinualStonesWinningCondition(
    val continualStonesStandard: ContinualStonesStandard,
    val continualStonesCondition: ContinualStonesCondition,
) : WinningCondition {
    fun canHaveDoubleRule(): Boolean = continualStonesStandard > ContinualStonesStandard(MIN_CONTINUAL_STONES_COUNT)

    fun canHaveOverlineRule(): Boolean = continualStonesCondition == ContinualStonesCondition.STRICT

    override fun isWin(
        board: Board,
        position: Position,
    ): Boolean = continualStonesCondition.win(ContinualStones.count(board, position), continualStonesStandard)
}
