package omok

import omok.controller.OmokController
import omok.domain.OmokGame
import omok.domain.grid.OmokGrid
import omok.view.InputView
import omok.view.OutputView

fun main() {
    val omokController = OmokController(InputView(), OutputView(), OmokGame(OmokGrid()))
    omokController.play()
}
