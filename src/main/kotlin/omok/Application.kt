package omok

import omok.controller.OmokControl
import omok.model.board.BoardSize
import omok.view.InputView
import omok.view.OutputView

fun main() {
    val boardSize = BoardSize(15)
    val inputView = InputView()
    val outputView = OutputView(boardSize)

    OmokControl(inputView, outputView, boardSize).run()
}
