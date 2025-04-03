package woowacourse.omok

import controller.OmokController
import view.InputView
import view.OutputView

fun main() {
    val omokController = OmokController(InputView(), OutputView())
    omokController.start()
}
