package omok.view

import omok.model.Board
import omok.model.IntersectionState

class OutputView {
    fun printOmokStart() {
        println(MESSAGE_OMOK_START)
        println()
    }

    fun printBoard(board: Board) {
        printBoardHeader(board)
        for (row in 2..14) {
            printBoardLine(row, board)
        }
        printBoardFooter(board)
    }

    private fun printBoardLine(
        row: Int,
        board: Board,
    ) {
        if (board.board[row][1] == IntersectionState.EMPTY) {
            print("├──")
        }

        for (column in 2..14) {
            when (board.board[row][column]) {
                IntersectionState.EMPTY -> print("┼──")
                IntersectionState.BLACK -> print("●──")
                IntersectionState.WHITE -> print("○──")
            }
        }

        if (board.board[row][1] == IntersectionState.EMPTY) {
            println("┤")
        }
    }

    private fun printBoardHeader(board: Board) {
        if (board.board[1][1] == IntersectionState.EMPTY) {
            print("┌──")
        }

        for (column in 2..14) {
            when (board.board[1][column]) {
                IntersectionState.EMPTY -> print("┬──")
                IntersectionState.BLACK -> print("●──")
                IntersectionState.WHITE -> print("○──")
            }
        }

        if (board.board[1][1] == IntersectionState.EMPTY) {
            println("┐")
        }
    }

    private fun printBoardFooter(board: Board) {
        if (board.board[1][1] == IntersectionState.EMPTY) {
            print("└──")
        }

        for (column in 2..14) {
            when (board.board[1][column]) {
                IntersectionState.EMPTY -> print("┴──")
                IntersectionState.BLACK -> print("●──")
                IntersectionState.WHITE -> print("○──")
            }
        }

        if (board.board[1][1] == IntersectionState.EMPTY) {
            println("┘")
        }
    }

    companion object {
        private const val MESSAGE_OMOK_START = "오목 게임을 시작합니다."
    }
}
