package woowacourse.omok

import omok.controller.OmokGame
import omok.view.InputView
import omok.view.OutputView

fun main() {
    val inputView = InputView()
    val outputView = OutputView()
    val omokGame = OmokGame(inputView, outputView)
    omokGame.start()
}
