package omok

import omok.controller.OmokController
import omok.model.ContinualStonesCondition
import omok.model.Stone
import omok.model.rule.ContinualStonesStandard
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
                continualStonesStandard = ContinualStonesStandard(5),
                continualStonesCondition = ContinualStonesCondition.EXACT,
            ),
        blackStoneForbiddenPlaces =
            listOf(
                DoubleFourForbiddenPlace(Stone.BLACK),
                DoubleOpenThreeForbiddenPlace(Stone.BLACK),
                OverlineForbiddenPlace(
                    continualStonesStandard = ContinualStonesStandard(5),
                ),
            ),
        whiteStoneForbiddenPlaces = listOf(),
    ).startGame()
}
