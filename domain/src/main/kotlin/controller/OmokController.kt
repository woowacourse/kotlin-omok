package controller

import domain.game.GameFinish
import domain.game.Omok
import domain.game.PutFailed
import domain.game.PutResult
import domain.game.PutSuccess
import domain.rule.OmokRule
import view.InputView
import view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    suspend fun startGame(blackRule: OmokRule, whiteRule: OmokRule) {
        val omok = Omok(blackRule, whiteRule)
        outputView.startGame()

        while (omok.canPlay) {
            outputView.printTurnStartMessage(omok.curPlayerColor, omok.lastPoint)
            takeTurn(omok) { putResult -> processPutResult(putResult) }
        }
    }

    private suspend fun takeTurn(omok: Omok, onPutStone: (PutResult) -> Unit) {
        inputView.askPosition { newPoint ->
            println("컨트롤러 takeTurn")
            val putResult = omok.put { newPoint }
            onPutStone(putResult)
        }
    }

    private fun processPutResult(result: PutResult) {
        when (result) {
            is PutSuccess -> outputView.drawPoint(result.stoneColor, result.point)
            is PutFailed -> outputView.printPutFailed()
            is GameFinish -> outputView.printResult(
                result.lastStoneColor,
                result.winnerStoneColor,
                result.point
            )
        }
    }
}
