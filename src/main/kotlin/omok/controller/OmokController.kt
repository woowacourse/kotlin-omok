package omok.controller

import omok.domain.board.OmokBoard
import omok.domain.point.Point
import omok.domain.stone.StoneColor
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val outputView: OutputView,
    private val inputView: InputView,
    private val omokBoard: OmokBoard,
) {
    fun startGame() {
        outputView.printStartMessage()
        var stone = StoneColor.BLACK
        while (omokBoard.isNotFull()) {
            val point = readValidPoint(stone)
            omokBoard.addStone(point)
            if (omokBoard.isOmok(point)) {
                outputView.printPrintWinner(stone)
                break
            }
            stone = stone.toggle()
        }
    }

    private fun readValidPoint(stone: StoneColor): Point {
        return retryWhenException(
            action = {
                val point = Point.of(getInputPoint(stone), stone)
                omokBoard.pointValidation(point)
                point
            },
            onError = outputView::printErrorMessage,
        )
    }

    private fun getInputPoint(stone: StoneColor): String {
        outputView.printBoard(omokBoard)
        return inputView.readStoneWithLatestStone(stone, omokBoard.latestStone)
    }
}
