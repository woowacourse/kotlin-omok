package woowacourse.omok.domain.omok

import woowacourse.omok.domain.omok.controller.OmokController
import woowacourse.omok.domain.omok.view.InputView
import woowacourse.omok.domain.omok.view.OutputView

fun main() {
    OmokController(
        inputView = InputView(),
        outputView = OutputView(),
    ).start()
}
