package omok.view

import omok.model.Board
import omok.model.IntersectionState

class OutputView {
    fun printOmokStart() {
        println(MESSAGE_OMOK_START)
        println()
    }

    private fun makeInitialBoard(
        rows: Int,
        cols: Int,
    ): String {
        val board = StringBuilder()

        board.append("%3d ".format(rows))
        board.append("┌──")
        for (col in 2..<cols) {
            board.append("┬──")
        }
        board.append("┐\n")

        for (row in rows - 1 downTo 2) {
            board.append("%3d ".format(row))
            board.append("├──")
            for (col in 2..<cols) {
                board.append("┼──")
            }
            board.append("┤\n")
        }

        board.append("%3d ".format(1))
        board.append("└──")
        for (col in 2..<cols) {
            board.append("┴──")
        }
        board.append("┘\n")

        val bottomLine = StringBuilder("  ")
        bottomLine.append(" ".repeat(rows.toString().length))
        for (alphabet in 1..cols) {
            bottomLine.append("${(alphabet + 64).toChar()}  ")
        }
        board.append(bottomLine)

        return board.toString()
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
        when (board.board[row][1]) {
            IntersectionState.EMPTY -> print("├──")
            IntersectionState.BLACK -> print("●──")
            IntersectionState.WHITE -> print("○──")
        }

        for (column in 2..14) {
            when (board.board[row][column]) {
                IntersectionState.EMPTY -> print("┼──")
                IntersectionState.BLACK -> print("●──")
                IntersectionState.WHITE -> print("○──")
            }
        }

        when (board.board[row][15]) {
            IntersectionState.EMPTY -> println("┤")
            IntersectionState.BLACK -> println("●")
            IntersectionState.WHITE -> println("○")
        }
    }

    private fun printBoardHeader(board: Board) {
        print("%3d ".format(15))
        when (board.board[15][1]) {
            IntersectionState.EMPTY -> print("┌──")
            IntersectionState.BLACK -> print("●──")
            IntersectionState.WHITE -> print("○──")
        }

        for (column in 2..14) {
            when (board.board[15][column]) {
                IntersectionState.EMPTY -> print("┬──")
                IntersectionState.BLACK -> print("●──")
                IntersectionState.WHITE -> print("○──")
            }
        }

        when (board.board[15][15]) {
            IntersectionState.EMPTY -> println("┐")
            IntersectionState.BLACK -> println("●")
            IntersectionState.WHITE -> println("○")
        }
    }

    private fun printBoardFooter(board: Board) {
        print("%3d ".format(1))
        when (board.board[1][1]) {
            IntersectionState.EMPTY -> print("└──")
            IntersectionState.BLACK -> print("●──")
            IntersectionState.WHITE -> print("○──")
        }

        for (column in 2..14) {
            when (board.board[1][column]) {
                IntersectionState.EMPTY -> print("┴──")
                IntersectionState.BLACK -> print("●──")
                IntersectionState.WHITE -> print("○──")
            }
        }

        when (board.board[1][15]) {
            IntersectionState.EMPTY -> println("┘")
            IntersectionState.BLACK -> println("●")
            IntersectionState.WHITE -> println("○")
        }

        println("    A  B  C  D  E  F  G  H  I  J  K  L  M  N  O")
    }

    companion object {
        private const val MESSAGE_OMOK_START = "오목 게임을 시작합니다."
    }
}
