package woowacourse.omok

import woowacourse.omok.controller.OmokController
import woowacourse.omok.view.ProgressView
import woowacourse.omok.view.ResultView
import woowacourse.omok.view.StartView
import woowacourse.omok.view.StonePositionView

fun main() {
    OmokController(
        StonePositionView(),
        StartView(),
        ProgressView(),
        ResultView(),
        boardSize = 15,
    ).startGame()
}
