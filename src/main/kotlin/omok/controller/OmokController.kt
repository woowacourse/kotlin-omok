package omok.controller

import omok.controller.ext.toggle
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
        var isFirst = true
        var stone = StoneStatus.BLACK
        while (omokBoard.isNotFull()) {
            val point =
                retryWhenException(
                    action = {
                        val point = readPoint(isFirst, stone)
                        omokBoard.addStone(point)
                        point
                    },
                    onError = outputView::printErrorMessage,
                )

            if (omokBoard.determineOmok(point)) {
                outputView.printPrintWinner(stone)
                break
            }

            isFirst = false
            stone = stone.toggle()
        }
    }

    private fun readPoint(
        isFirst: Boolean,
        stone: StoneStatus,
    ): Point {
        return retryWhenException(
            action = {
                val pos = getInputPoint(isFirst, stone)
                val col = pos[0].uppercaseChar()
                val row = pos.substring(1)
                Point.of(row, col, stone)
            },
            onError = outputView::printErrorMessage,
        )
    }

    private fun getInputPoint(
        isFirst: Boolean,
        stone: StoneStatus,
    ): String {
        return if (isFirst) {
            outputView.printStartMessage()
            outputView.printBoard(omokBoard)
            inputView.readStoneWithLastPosition(stone, null)
        } else {
            outputView.printBoard(omokBoard)
            inputView.readStoneWithLastPosition(stone, omokBoard.getLatestStone())
        }
    }
}
