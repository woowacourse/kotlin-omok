package omok

import omok.controller.OmokController
import omok.model.DoubleFourForbiddenPlace
import omok.model.DoubleOpenThreeForbiddenPlace
import omok.model.OverlineForbiddenPlace
import omok.model.rule.StoneForbiddenPlaces
import omok.view.InputView
import omok.view.OutputView

fun main() {
    OmokController(
        InputView(),
        OutputView(),
        StoneForbiddenPlaces(
            blackForbiddenPlaces = listOf(DoubleFourForbiddenPlace(), DoubleOpenThreeForbiddenPlace(), OverlineForbiddenPlace()),
            whiteForbiddenPlaces = listOf()
        )
    ).startGame()
}
