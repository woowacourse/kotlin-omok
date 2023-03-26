package woowacourse.omok.controller

import domain.game.GameFinish
import domain.game.Omok
import domain.game.PutFailed
import domain.game.PutResult
import domain.game.PutSuccess
import domain.rule.OmokRule
import woowacourse.omok.view.InputView
import woowacourse.omok.view.OutputView

class ConsoleOmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    suspend fun startGame(blackRule: OmokRule, whiteRule: OmokRule) {
        val omok = Omok(blackRule, whiteRule)
        outputView.startGame()

        while (omok.canPlay) {
            outputView.showThisTurn(omok.curPlayerColor, omok.lastPoint)
            takeTurn(omok) { putResult -> processPutResult(putResult) }
        }
    }

    suspend fun startGame(omok: Omok) {
        outputView.startGame()

        while (omok.canPlay) {
            outputView.showThisTurn(omok.curPlayerColor, omok.lastPoint)
            takeTurn(omok) { putResult -> processPutResult(putResult) }
        }
    }

    private suspend fun takeTurn(omok: Omok, onPutStone: (PutResult) -> Unit) {
        inputView.readPosition { newPoint ->
            val putResult = omok.put { newPoint }
            onPutStone(putResult)
        }
    }

    private fun processPutResult(result: PutResult) {
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
