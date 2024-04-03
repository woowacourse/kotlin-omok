package woowacourse.omok.console

import woowacourse.omok.console.controller.OmokController
import woowacourse.omok.console.view.ProgressView
import woowacourse.omok.console.view.ResultView
import woowacourse.omok.console.view.StartView
import woowacourse.omok.console.view.StonePositionView

fun main() {
    OmokController(
        StonePositionView(),
        StartView(),
        ProgressView(),
        ResultView(),
        boardSize = 15,
    ).startGame()
}
