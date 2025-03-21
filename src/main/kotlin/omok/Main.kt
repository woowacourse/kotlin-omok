package omok

import omok.controller.OmokController
import omok.domain.board.OmokBoard
import omok.domain.point.OmokPoints
import omok.domain.rule.finder.DfsRenjuFinder
import omok.domain.rule.renjuRule.RenjuRule
import omok.view.InputView
import omok.view.OutputView

fun main() {
    val inputView = InputView()
    val outputView = OutputView()
    val points = OmokPoints()
    val rule = RenjuRule(DfsRenjuFinder)
    val omokBoard = OmokBoard(points, rule)
    val controller = OmokController(outputView, inputView, omokBoard, rule)
    controller.startGame()
}
