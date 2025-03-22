package omok

import omok.controller.OmokController
import omok.domain.board.OmokBoard
import omok.domain.point.OmokPoints
import omok.domain.service.OmokGame
import omok.view.InputView
import omok.view.OutputView

fun main() {
    val inputView = InputView()
    val outputView = OutputView()
    val points = OmokPoints()
    val omokBoard = OmokBoard(points)
    val omokGame = OmokGame(omokBoard)
    val controller = OmokController(outputView, inputView, omokGame)
    controller.run()
}
