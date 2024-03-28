package woowacourse.omok.src.main.kotlin.omok

import woowacourse.omok.src.main.kotlin.omok.controller.OmokController
import woowacourse.omok.src.main.kotlin.omok.view.InputView
import woowacourse.omok.src.main.kotlin.omok.view.OutputView

fun main() {
    val controller = OmokController(InputView(), OutputView())
    controller.start()
}
