package omok

import omok.controller.OmokController
import omok.model.ContinualStonesCondition
import omok.model.Stone
import omok.model.rule.ContinualStonesStandard
import omok.model.rule.GamePlayingRules
import omok.model.rule.ban.DoubleFourForbiddenPlace
import omok.model.rule.ban.DoubleOpenThreeForbiddenPlace
import omok.model.rule.ban.ForbiddenPlaces
import omok.model.rule.ban.OverlineForbiddenPlace
import omok.model.rule.winning.ContinualStonesWinningCondition
import omok.view.InputView
import omok.view.OutputView

fun main() {
    OmokController(
        InputView(),
        OutputView(),
        blackStoneGamePlayingRules =
            GamePlayingRules.from(
                continualStonesWinningCondition =
                    ContinualStonesWinningCondition(
                        ContinualStonesStandard(5),
                        ContinualStonesCondition.EXACT,
                    ),
                forbiddenPlaces =
                    ForbiddenPlaces(
                        DoubleOpenThreeForbiddenPlace(Stone.BLACK),
                        DoubleFourForbiddenPlace(Stone.BLACK),
                        OverlineForbiddenPlace(),
                    ),
            ),
        whiteStoneGamePlayingRules =
            GamePlayingRules.from(
                continualStonesWinningCondition =
                    ContinualStonesWinningCondition(
                        ContinualStonesStandard(5),
                        ContinualStonesCondition.EXACT,
                    ),
                forbiddenPlaces = ForbiddenPlaces(),
            ),
    ).startGame()
}
