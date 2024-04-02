package woowacourse.omok.controller

import woowacourse.omok.model.OmokGame
import woowacourse.omok.view.input.InputView
import woowacourse.omok.view.output.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
    private val omokGame: OmokGame,
) {
    fun startOmok() {
        outputView.printAlert("오목 게임을 시작합니다")
        omokGame.run(inputView::getStonePoint, outputView::printTurn, outputView::printWinner, outputView::printAlert)
    }
}
