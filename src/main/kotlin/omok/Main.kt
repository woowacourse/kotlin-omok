package omok

import omok.controller.OmokController
import omok.domain.board.OmokBoard
import omok.domain.point.OmokPoints
import omok.domain.rule.renjuRule.RenjuRule
import omok.view.InputView
import omok.view.OutputView

fun main() {
    val inputView = InputView()
    val outputView = OutputView()
    val points = OmokPoints()
    val omokBoard = OmokBoard(points, RenjuRule)
    val controller = OmokController(outputView, inputView, omokBoard, RenjuRule)
    controller.startGame()
}
