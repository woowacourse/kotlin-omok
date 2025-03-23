package omok

import omok.controller.OmokControl
import omok.model.board.BoardSize
import omok.view.InputView
import omok.view.OutputView

fun main() {
    val inputView = InputView()
    val outputView = OutputView()
    val boardSize = BoardSize(15)

    OmokControl(inputView, outputView, boardSize).run()
}
