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
    fun startGame() {
        outputView.printStartMessage()
        var stone = StoneStatus.BLACK
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

    private fun readValidPoint(stoneColor: StoneStatus): Point {
        return retryWhenException(
            action = {
                val point = Point.of(getInputPoint(stoneColor), stoneColor)
                omokBoard.pointValidation(point)
                point
            },
            onError = outputView::printErrorMessage,
        )
    }

    private fun getInputPoint(stoneColor: StoneStatus): String {
        outputView.printBoard(omokBoard)
        return inputView.readStoneWithLatestStone(stoneColor, omokBoard.latestStone)
    }
}
