package omok.controller

import omok.domain.board.OmokBoard
import omok.domain.point.Black
import omok.domain.point.Point
import omok.domain.rule.OmokRule
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val outputView: OutputView,
    private val inputView: InputView,
    private val omokBoard: OmokBoard,
    private val omokRule: OmokRule,
) {
    fun startGame() {
        outputView.printStartMessage()
        var stone: Point = Black(readValidPoint())
        while (omokBoard.isNotFull()) {
            omokBoard.addStone(stone)
            if (omokRule.isOmok(stone, omokBoard)) {
                outputView.printPrintWinner(stone)
                break
            }
            stone = stone.toggle(readValidPoint())
        }
    }

    private fun readValidPoint(): String {
        return retryWhenException(
            action = {
                getInputPoint()
            },
            onError = outputView::printErrorMessage,
        )
    }

    private fun getInputPoint(): String {
        outputView.printBoard(omokBoard)
        return inputView.readStoneWithLastPosition(omokBoard.latestStone)
    }
}
