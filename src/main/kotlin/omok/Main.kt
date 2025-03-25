package omok

import omok.controller.OmokController
import omok.domain.board.OmokBoard
import omok.domain.rule.finder.DfsRenjuFinder
import omok.domain.rule.renjuRule.RenjuRule
import omok.domain.stone.OmokStones
import omok.view.InputView
import omok.view.OutputView

fun main() {
    val points = OmokStones()
    val rule = RenjuRule(DfsRenjuFinder)
    val omokBoard = OmokBoard(points, rule)
    val controller = OmokController(OutputView, InputView, omokBoard, rule)
    controller.startGame()
}
