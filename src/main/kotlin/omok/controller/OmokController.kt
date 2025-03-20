package omok.controller

import omok.domain.board.OmokBoard
import omok.domain.board.StoneStatus
import omok.domain.point.Point
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val outputView: OutputView,
    private val inputView: InputView,
    private val omokBoard: OmokBoard,
) {
    fun run() {
        startGame(omokBoard)
    }

    private fun startGame(omokBoard: OmokBoard) {
        outputView.printStartMessage()
        var stone = StoneStatus.BLACK
        while (omokBoard.isNotFull()) {
            val point = readValidPoint(stone)

            omokBoard.addStone(point)
            if (omokBoard.determineOmok(point)) {
                outputView.printPrintWinner(stone)
                break
            }
            stone = stone.toggle()
        }
    }

    private fun readValidPoint(stone: StoneStatus): Point {
        return retryWhenException(
            action = {
                val point = Point.of(getInputPoint(), stone)
                omokBoard.pointValidation(point)
                point
            },
            onError = outputView::printErrorMessage,
        )
    }

    private fun getInputPoint(): String {
        outputView.printBoard(omokBoard)
        return inputView.readStoneWithLastPosition(omokBoard.latestStone)
    }
}
