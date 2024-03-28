package omok

import omok.controller.OmokController
import omok.model.ContinualStonesCondition
import omok.model.rule.ContinualStonesStandard
import omok.model.rule.ForbiddenRules
import omok.model.rule.RuleAdapter
import omok.model.rule.library.FourFourRule
import omok.model.rule.library.OverlineRule2
import omok.model.rule.library.ThreeThreeRule
import omok.model.rule.winning.ContinualStonesWinningCondition
import omok.view.InputView
import omok.view.OutputView

fun main() {
    OmokController(
        InputView(),
        OutputView(),
        blackStoneGamePlayingRules = RuleAdapter(
            ContinualStonesWinningCondition(ContinualStonesStandard(4), ContinualStonesCondition.EXACT),
            ForbiddenRules(
                listOf(
                    ThreeThreeRule.forBlack(),
                    FourFourRule.forBlack(),
                    OverlineRule2(),
                ),
            ),
        ),
        whiteStoneGamePlayingRules = RuleAdapter(
            ContinualStonesWinningCondition(ContinualStonesStandard(6), ContinualStonesCondition.EXACT),
            ForbiddenRules(
                listOf(
                    ThreeThreeRule.forWhite(),
                    FourFourRule.forWhite(),
                    OverlineRule2(),
                ),
            ),
        ),
    ).startGame()
}
