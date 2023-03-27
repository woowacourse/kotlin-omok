package woowacourse.omok.controller

import domain.game.GameFinish
import domain.game.Omok
import domain.game.PutFailed
import domain.game.PutResult
import domain.game.PutSuccess
import domain.rule.OmokRule
import woowacourse.omok.console.InputView
import woowacourse.omok.console.OutputView

abstract class OmokController(
    protected val inputView: InputView,
    protected val outputView: OutputView,
) {
    abstract suspend fun startGame(blackRule: OmokRule, whiteRule: OmokRule)

    protected suspend fun takeTurn(omok: Omok, onPutStone: (PutResult) -> Unit) {
        val newPoint = inputView.readPosition()
        val putResult = omok.put { newPoint }
        onPutStone(putResult)
    }

    protected fun processPutResult(result: PutResult) {
        when (result) {
            is PutSuccess -> outputView.drawStone(result.stoneColor, result.point)
            is PutFailed -> outputView.showPutFailed()
            is GameFinish -> outputView.showResult(
                result.lastStoneColor,
                result.winnerStoneColor,
                result.point
            )
        }
    }
}
