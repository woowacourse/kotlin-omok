package woowacourse.omok.domain

import woowacourse.omok.domain.controller.OmokController
import woowacourse.omok.domain.model.ContinualStonesCondition
import woowacourse.omok.domain.model.rule.ContinualStonesStandard
import woowacourse.omok.domain.model.rule.ForbiddenRules
import woowacourse.omok.domain.model.rule.RuleAdapter
import woowacourse.omok.domain.model.rule.library.FourFourRule
import woowacourse.omok.domain.model.rule.library.OverlineRule
import woowacourse.omok.domain.model.rule.library.ThreeThreeRule
import woowacourse.omok.domain.model.rule.winning.ContinualStonesWinningCondition
import woowacourse.omok.domain.view.InputView
import woowacourse.omok.domain.view.OutputView

fun main() {
    OmokController(
        InputView(),
        OutputView(),
        blackStoneGamePlayingRules =
            RuleAdapter(
                ContinualStonesWinningCondition(ContinualStonesStandard(5), ContinualStonesCondition.EXACT),
                ForbiddenRules(
                    listOf(
                        ThreeThreeRule.forBlack(),
                        FourFourRule.forBlack(),
                        OverlineRule(),
                    ),
                ),
            ),
        whiteStoneGamePlayingRules =
            RuleAdapter(
                ContinualStonesWinningCondition(ContinualStonesStandard(5), ContinualStonesCondition.CAN_OVERLINE),
                ForbiddenRules(),
            ),
    ).startGame2()
}
