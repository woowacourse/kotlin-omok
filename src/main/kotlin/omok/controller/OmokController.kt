package omok.controller

import omok.model.game.Board
import omok.model.game.OmokGame
import omok.view.InputView
import omok.view.OutputView

class OmokController {
    fun run() {
        val board = Board()
        val omokGame = OmokGame(board)
        OutputView.printInitGame()
        val result = omokGame.start(
            coordinate = InputView::getCoordinate,
            showBoard = OutputView::printBoard,
            showTurn = OutputView::printTurn
        )

        OutputView.printResult(result)
    }
}
