package omok.controller

import omok.model.Board
import omok.view.OutputView

class Controller {
    fun play() {
        OutputView.printBoard(Board())
    }
}
