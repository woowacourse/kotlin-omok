package omok

import omok.controller.OmokController
import omok.domain.board.OmokBoard
import omok.domain.rule.OmokRules
import omok.domain.rule.finder.DfsRenjuFinder
import omok.domain.rule.renjuRule.RenjuRule
import omok.domain.stone.OmokStones
import omok.view.InputView
import omok.view.OutputView

fun main() {
    val points = OmokStones()
    val rules =
        object : OmokRules {
            override val rules = listOf(RenjuRule(DfsRenjuFinder))
        }
    val omokBoard = OmokBoard(points, rules)
    val controller = OmokController(OutputView, InputView, omokBoard, rules)
    controller.startGame()
}
