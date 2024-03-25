package omok

import omok.controller.OmokController
import omok.model.ContinualStonesCondition
import omok.model.rule.ban.DoubleFourForbiddenPlace
import omok.model.rule.ban.DoubleOpenThreeForbiddenPlace
import omok.model.rule.ban.OverlineForbiddenPlace
import omok.model.rule.winning.ContinualStonesWinningCondition
import omok.view.InputView
import omok.view.OutputView

fun main() {
    OmokController(
        InputView(),
        OutputView(),
        winningCondition =
            ContinualStonesWinningCondition(
                continualStonesStandard = 5,
                continualStonesCondition = ContinualStonesCondition.EXACT,
            ),
        blackStoneForbiddenPlaces =
            listOf(
                DoubleFourForbiddenPlace(),
                DoubleOpenThreeForbiddenPlace(),
                OverlineForbiddenPlace(
                    continualStonesStandard = 5,
                ),
            ),
        whiteStoneForbiddenPlaces = listOf(),
    ).startGame()
}
