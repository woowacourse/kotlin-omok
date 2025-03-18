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
        for (row in 14 downTo 2) {
            printBoardLine(row, board)
        }
        printBoardFooter(board)
    }

    private fun printBoardLine(
        row: Int,
        board: Board,
    ) {
        print("%3d ".format(row))
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
        print("%3d ".format(15))
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
        print("%3d ".format(1))
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

        if (board.board[1][15] == IntersectionState.EMPTY) {
            println("┘")
        }

        println("    A  B  C  D  E  F  G  H  I  J  K  L  M  N  O")
    }

    companion object {
        private const val MESSAGE_OMOK_START = "오목 게임을 시작합니다."
    }
}
