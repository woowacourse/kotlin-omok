package woowacourse.omok

import woowacourse.omok.controller.OmokController
import woowacourse.omok.view.InputView
import woowacourse.omok.view.OutputView

fun main() {
    OmokController(
        inputView = InputView(),
        outputView = OutputView(),
    ).start()
}
