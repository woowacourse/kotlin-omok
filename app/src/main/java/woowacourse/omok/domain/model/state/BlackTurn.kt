package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.ContinualStonesCondition
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StonePosition
import woowacourse.omok.domain.model.rule.ContinualStonesStandard
import woowacourse.omok.domain.model.rule.ForbiddenRules
import woowacourse.omok.domain.model.rule.RuleAdapter
import woowacourse.omok.domain.model.rule.library.FourFourRule
import woowacourse.omok.domain.model.rule.library.OverlineRule
import woowacourse.omok.domain.model.rule.library.ThreeThreeRule
import woowacourse.omok.domain.model.rule.winning.ContinualStonesWinningCondition

data class BlackTurn(private val latestStonePosition: StonePosition) : PlayerTurn(latestStonePosition) {
    override val stone: Stone
        get() = Stone.BLACK
    override val rule: RuleAdapter = blackRenjuRule

    override fun nextTurn(position: Position): GameState = WhiteTurn(latestStonePosition = StonePosition(position, stone))

    companion object {
        private val blackRenjuRule =
            RuleAdapter(
                OverlineRule.forBlack(
                    ContinualStonesWinningCondition(
                        ContinualStonesStandard(5),
                        ContinualStonesCondition.STRICT,
                    ),
                ),
                ForbiddenRules(
                    ThreeThreeRule.forBlack(),
                    FourFourRule.forBlack(),
                ),
            )
    }
}
