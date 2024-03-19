package omok.view.output

import omok.model.Board

interface OutputView {
    fun printStartGuide()

    fun printOneTurn(board: Board)
}
