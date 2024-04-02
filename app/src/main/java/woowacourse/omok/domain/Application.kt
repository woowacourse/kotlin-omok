package woowacourse.omok.domain

import woowacourse.omok.domain.controller.OmokController2
import woowacourse.omok.domain.view.InputView
import woowacourse.omok.domain.view.OutputView

fun main() {
    OmokController2(
        InputView(),
        OutputView()
    ).startGame2()
}
