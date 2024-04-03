package woowacourse.omok.domain

import woowacourse.omok.domain.controller.OmokController
import woowacourse.omok.domain.view.InputView
import woowacourse.omok.domain.view.OutputView

fun main() {
    OmokController(
        InputView(),
        OutputView()
    ).startGame()
}
