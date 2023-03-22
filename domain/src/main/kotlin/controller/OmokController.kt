package controller

import domain.game.*
import domain.point.Point
import domain.rule.OmokRule
import view.OmokInputView
import view.OmokOutputView

class OmokController(
    private val inputView: OmokInputView,
    private val outputView: OmokOutputView,
) {
    fun startGame(blackRule: OmokRule, whiteRule: OmokRule) {
        val omok = Omok(blackRule, whiteRule)
        outputView.startGame()

        while (true) {
            outputView.printTurnStartMessage(omok.curPlayerColor, omok.lastPoint)
            val isFinished = takeTurn(omok)
            if (isFinished) break
        }
    }

    private fun takeTurn(omok: Omok): Boolean {
        val newPoint = inputView.askPosition()
        val result = omok.put { newPoint }

        processPutResult(result, newPoint)
        return result is GameFinish
    }

    private fun processPutResult(result: PutResult, newPoint: Point) {
        when (result) {
            is PutSuccess -> outputView.drawPoint(result.stoneColor, newPoint)
            is PutFailed -> outputView.printPutFailed()
            is GameFinish -> outputView.printResult(result.lastStoneColor, result.winnerStoneColor, newPoint)
        }
    }
}
