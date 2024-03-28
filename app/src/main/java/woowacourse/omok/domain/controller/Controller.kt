package woowacourse.omok.domain.controller

import woowacourse.omok.domain.model.OmokGame
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.view.InputView
import woowacourse.omok.domain.view.OutputView

class Controller {
    fun run() {
        OutputView.printGameStart()
        OmokGame().play(OutputView::printBoard, OutputView::printTurn, ::getPoint)
    }

    private fun getPoint(): Point = runCatching { InputView.readPoint() }.getOrNull() ?: getPoint()
}
