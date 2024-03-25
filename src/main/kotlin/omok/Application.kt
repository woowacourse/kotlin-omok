package omok

import omok.controller.OmokController
import omok.model.rule.ban.DoubleFourForbiddenPlace
import omok.model.rule.ban.DoubleOpenThreeForbiddenPlace
import omok.model.rule.ban.OverlineForbiddenPlace
import omok.model.rule.winning.AllForbiddenPositionFinishCondition
import omok.model.rule.winning.FiveStonesFinishCondition
import omok.model.rule.winning.FullBoardFinishCondition
import omok.view.InputView
import omok.view.OutputView

fun main() {
    OmokController(
        InputView(),
        OutputView(),
        boardSize = 15,
        finishConditions =
            listOf(
                FiveStonesFinishCondition(),
                FullBoardFinishCondition(),
                AllForbiddenPositionFinishCondition(),
            ),
        blackStoneForbiddenPlaces =
            listOf(
                DoubleFourForbiddenPlace(),
                DoubleOpenThreeForbiddenPlace(),
                OverlineForbiddenPlace(),
            ),
        whiteStoneForbiddenPlaces = listOf(),
    ).startGame()
}
