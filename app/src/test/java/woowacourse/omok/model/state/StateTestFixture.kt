package woowacourse.omok.model.state

import woowacourse.omok.domain.model.ContinualStonesCondition
import woowacourse.omok.domain.model.rule.ContinualStonesStandard
import woowacourse.omok.domain.model.rule.ForbiddenRules
import woowacourse.omok.domain.model.rule.RuleAdapter
import woowacourse.omok.domain.model.rule.library.FourFourRule
import woowacourse.omok.domain.model.rule.library.OverlineRule
import woowacourse.omok.domain.model.rule.library.ThreeThreeRule
import woowacourse.omok.domain.model.rule.winning.ContinualStonesWinningCondition

object StateTestFixture {
    val blackStoneRenjuRule = RuleAdapter(
        ContinualStonesWinningCondition(ContinualStonesStandard(5), ContinualStonesCondition.EXACT),
        ForbiddenRules(
            ThreeThreeRule.forBlack(),
            FourFourRule.forBlack(),
            OverlineRule()
        )
    )

    val whiteStoneRenjuRule = RuleAdapter(
        ContinualStonesWinningCondition(ContinualStonesStandard(5), ContinualStonesCondition.EXACT),
        ForbiddenRules()
    )
}