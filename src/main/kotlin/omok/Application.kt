package omok

import omok.controller.OmokController
import omok.model.DoubleFourForbiddenPlace
import omok.model.DoubleOpenThreeForbiddenPlace
import omok.model.OverlineForbiddenPlace
import omok.view.InputView
import omok.view.OutputView

fun main() {
    OmokController(
        InputView(),
        OutputView(),
        blackStoneForbiddenPlaces = listOf(
            DoubleFourForbiddenPlace(),
            DoubleOpenThreeForbiddenPlace(),
            OverlineForbiddenPlace()
        ),
        whiteStoneForbiddenPlaces = listOf(),
    ).startGame()
}
