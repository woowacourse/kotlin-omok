package omok

import omok.controller.OmokController
import omok.domain.board.OmokBoard
import omok.domain.game.OmokGame
import omok.domain.rule.OmokRules
import omok.domain.rule.finder.DfsRenjuFinder
import omok.domain.rule.renjuRule.RenjuRule
import omok.domain.stone.OmokStones
import omok.event.OmokEventListener
import omok.view.InputView
import omok.view.OutputView

fun main() {
    val points = OmokStones()
    val rules =
        object : OmokRules {
            override val rules = listOf(RenjuRule(DfsRenjuFinder))
        }
    val omokBoard = OmokBoard(points, rules)
    val omokGame = OmokGame(omokBoard, rules)
    val event = OmokEventListener(OutputView, InputView)
    val controller = OmokController(omokGame, event)
    controller.startGame()
}
