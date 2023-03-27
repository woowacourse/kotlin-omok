package woowacourse.omok.controller

import domain.game.Omok
import domain.rule.OmokRule
import woowacourse.omok.console.InputView
import woowacourse.omok.console.OutputView

class ConsoleOmokController(inputView: InputView, outputView: OutputView) :
    OmokController(inputView, outputView) {

    override suspend fun startGame(blackRule: OmokRule, whiteRule: OmokRule) {
        val omok = Omok(blackRule, whiteRule)
        outputView.startGame()

        while (omok.canPlay()) {
            takeTurn(omok) { putResult ->
                processPutResult(putResult)
                outputView.showCurrentTurnColor(omok.curPlayerColor, omok.lastPoint)
            }
        }
    }
}
