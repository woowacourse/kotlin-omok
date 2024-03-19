package omok.controller

import omok.model.Board
import omok.view.InputView
import omok.view.OutputView

class Controller {
    fun play() {
        val board = Board()
        OutputView.printBoard(board)
        val point = InputView.readPoint()
    }
}
