package omok.controller

import omok.model.BlackTurn
import omok.model.Board
import omok.model.WhiteTurn
import omok.view.OutputView

class Controller {
    fun play() {
        val board = Board()
        OutputView.printBoard(board)

        BlackTurn()
        WhiteTurn()
    }
}
