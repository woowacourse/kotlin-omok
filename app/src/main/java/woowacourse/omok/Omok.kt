package woowacourse.omok

import woowacourse.omok.controller.OmokController
import woowacourse.omok.domain.OmokGame
import woowacourse.omok.domain.grid.OmokGrid
import woowacourse.omok.view.InputView
import woowacourse.omok.view.OutputView

fun main() {
    val omokController = OmokController(InputView(), OutputView(), OmokGame(OmokGrid(setOf())))
    omokController.play()
}
