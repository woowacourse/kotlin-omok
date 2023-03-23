package view

import domain.OmokGame

object OutputView {

    fun printBoard(omokGame: OmokGame) {
        return println(BoardView(omokGame))
    }
}
