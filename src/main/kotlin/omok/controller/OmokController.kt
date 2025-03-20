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
            val point =
                retryWhenException(
                    action = {
                        val point = readPoint(stone)
                        omokBoard.addStone(point)
                        point
                    },
                    onError = outputView::printErrorMessage,
                )

            if (omokBoard.determineOmok(point)) {
                outputView.printPrintWinner(stone)
                break
            }

            stone = stone.toggle()
        }
    }

    private fun readPoint(stone: StoneStatus): Point {
        return retryWhenException(
            action = {
                val pos = getInputPoint(stone)
                Point.of(pos, stone)
            },
            onError = outputView::printErrorMessage,
        )
    }

    private fun getInputPoint(stone: StoneStatus): String {
        outputView.printBoard(omokBoard)
        val latestStone = omokBoard.getLatestStone()
        return inputView.readStoneWithLastPosition(stone, latestStone)
    }
}
