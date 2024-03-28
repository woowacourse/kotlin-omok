package omok

import omok.controller.OmokController
import omok.model.ContinualStonesCondition
import omok.model.rule.ContinualStonesStandard
import omok.model.rule.ForbiddenRules
import omok.model.rule.RuleAdapter
import omok.model.rule.library.FourFourRule
import omok.model.rule.library.OverlineRule
import omok.model.rule.library.ThreeThreeRule
import omok.model.rule.winning.ContinualStonesWinningCondition
import omok.view.InputView
import omok.view.OutputView

fun main() {
    OmokController(
        InputView(),
        OutputView(),
        blackStoneGamePlayingRules = RuleAdapter(
            ContinualStonesWinningCondition(ContinualStonesStandard(5), ContinualStonesCondition.EXACT),
            ForbiddenRules(
                listOf(
                    ThreeThreeRule.forBlack(),
                    FourFourRule.forBlack(),
                    OverlineRule(),
                ),
            ),
        ),
        whiteStoneGamePlayingRules = RuleAdapter(
            ContinualStonesWinningCondition(ContinualStonesStandard(5), ContinualStonesCondition.CAN_OVERLINE),
            ForbiddenRules(),
        ),
    ).startGame()
}
