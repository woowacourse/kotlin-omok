package woowacourse.omok

import woowacourse.omok.controller.OmokController
import woowacourse.omok.model.OmokGame
import woowacourse.omok.model.entity.StoneColor
import woowacourse.omok.view.input.ConsoleInputView
import woowacourse.omok.view.output.ConsoleOutputView

fun main() {
    val inputView = ConsoleInputView()
    val outputView = ConsoleOutputView()
    val omokGame = OmokGame.of(StoneColor.BLACK)
    val omokController = OmokController(inputView, outputView, omokGame)
    omokController.startOmok()
}
