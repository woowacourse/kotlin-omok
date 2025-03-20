package omok

import omok.controller.OmokController
import omok.domain.board.OmokBoard
import omok.domain.point.OmokPoints
import omok.view.InputView
import omok.view.OutputView

fun main() {
    val inputView = InputView()
    val outputView = OutputView()
    val points = OmokPoints()
    val omokBoard = OmokBoard(points)
    val controller = OmokController(outputView, inputView, omokBoard)
    controller.startGame()
}
