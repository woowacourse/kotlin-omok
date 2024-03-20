package omok.controller

import omok.model.Board
import omok.model.Position
import omok.model.Stone
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun run() {
        val board = initializedBoard()

        var recentStone = Stone.BLACK
        var recentPosition: Position? = null

        while (true) {
            recentPosition = inputView.readStonePosition(recentStone, recentPosition)
            board.place(recentPosition, recentStone)
            outputView.printBoard(board)
            if (board.isWin(recentPosition)) break
            recentStone = recentStone.next()
        }
        outputView.printWinner(recentStone)
    }

    private fun initializedBoard(): Board {
        return Board().apply { outputView.printInitialGuide(this) }
    }
}
