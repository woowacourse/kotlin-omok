package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.ContinualStonesCondition
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StonePosition
import woowacourse.omok.domain.model.rule.ContinualStonesStandard
import woowacourse.omok.domain.model.rule.ForbiddenRules
import woowacourse.omok.domain.model.rule.RuleAdapter
import woowacourse.omok.domain.model.rule.library.NoneForbiddenRule
import woowacourse.omok.domain.model.rule.winning.ContinualStonesWinningCondition

class WhiteTurn(latestStonePosition: StonePosition) : Turn(latestStonePosition) {
    override val stone: Stone
        get() = Stone.WHITE
    override val rule: RuleAdapter = whiteRenjuRule

    override fun nextTurn(position: Position): GameState = BlackTurn(latestStonePosition = StonePosition(position, stone))

    companion object {
        private val whiteRenjuRule =
            RuleAdapter(
                ContinualStonesWinningCondition(ContinualStonesStandard(5), ContinualStonesCondition.EXACT),
                ForbiddenRules(
                    NoneForbiddenRule,
                ),
            )
    }
}
