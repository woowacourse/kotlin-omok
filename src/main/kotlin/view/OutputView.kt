package view

import domain.Board

object OutputView {

    fun printBoard(board: Board) {
        return println(BoardView(board))
    }
}
