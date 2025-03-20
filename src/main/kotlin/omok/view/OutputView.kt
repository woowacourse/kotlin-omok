package omok.view

import omok.model.Board
import omok.model.IntersectionState
import omok.model.StoneColor
import rule.wrapper.point.Point

class OutputView {
    fun printOmokStart() {
        println(MESSAGE_OMOK_START)
        println()
    }

    fun modifyBoard(
        whitePoints: List<Point>,
        blackPoints: List<Point>,
    ): String {
        val board: String = makeInitialBoard(BOARD_SIZE, BOARD_SIZE)
        val lines = board.lines().toMutableList()
        whitePoints.forEach { (row, col) ->
            lines[BOARD_SIZE - row] = modifyLine(col, lines[BOARD_SIZE - row], StoneColor.WHITE)
        }
        blackPoints.forEach { (row, col) ->
            lines[BOARD_SIZE - row] = modifyLine(col, lines[BOARD_SIZE - row], StoneColor.BLACK)
        }

        return lines.joinToString("\n")
    }

    private fun modifyLine(
        col: Int,
        line: String,
        color: StoneColor,
    ): String {
        val x = col - 1
        val sb = StringBuilder(line)
        sb[x + ROW_NUMBER_OFFSET_SIZE + x * COLUMN_NUMBER_OFFSET_SIZE] =
            when (color) {
                StoneColor.BLACK -> BLACK_STONE
                StoneColor.WHITE -> WHITE_STONE
            }
        return sb.toString()
    }

    private fun makeInitialBoard(
        rows: Int,
        cols: Int,
    ): String {
        val board = StringBuilder()

        board.append(FORMAT_ROW_NUMBER.format(rows))
        board.append("┌─")
        for (col in 2..<cols) {
            board.append("─┬─")
        }
        board.append("┐\n")

        for (row in rows - 1 downTo 2) {
            board.append(FORMAT_ROW_NUMBER.format(row))
            board.append("─├─")
            for (col in 2..<cols) {
                board.append("─┼─")
            }
            board.append("┤\n")
        }

        board.append(FORMAT_ROW_NUMBER.format(1))
        board.append("└─")
        for (col in 2..<cols) {
            board.append("─┴─")
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
        print(FORMAT_ROW_NUMBER.format(row))
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

        when (board.board[row][BOARD_SIZE]) {
            IntersectionState.EMPTY -> println("┤")
            IntersectionState.BLACK -> println("●")
            IntersectionState.WHITE -> println("○")
        }
    }

    private fun printBoardHeader(board: Board) {
        print(FORMAT_ROW_NUMBER.format(BOARD_SIZE))
        when (board.board[BOARD_SIZE][1]) {
            IntersectionState.EMPTY -> print("┌──")
            IntersectionState.BLACK -> print("●──")
            IntersectionState.WHITE -> print("○──")
        }

        for (column in 2..14) {
            when (board.board[BOARD_SIZE][column]) {
                IntersectionState.EMPTY -> print("┬──")
                IntersectionState.BLACK -> print("●──")
                IntersectionState.WHITE -> print("○──")
            }
        }

        when (board.board[BOARD_SIZE][BOARD_SIZE]) {
            IntersectionState.EMPTY -> println("┐")
            IntersectionState.BLACK -> println("●")
            IntersectionState.WHITE -> println("○")
        }
    }

    private fun printBoardFooter(board: Board) {
        print(FORMAT_ROW_NUMBER.format(1))
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

        when (board.board[1][BOARD_SIZE]) {
            IntersectionState.EMPTY -> println("┘")
            IntersectionState.BLACK -> println("●")
            IntersectionState.WHITE -> println("○")
        }

        println("    A  B  C  D  E  F  G  H  I  J  K  L  M  N  O")
    }

    companion object {
        private const val MESSAGE_OMOK_START = "오목 게임을 시작합니다."
        private const val BOARD_SIZE = 15
        private const val ROW_NUMBER_OFFSET_SIZE = 4
        private const val COLUMN_NUMBER_OFFSET_SIZE = 2
        private const val FORMAT_ROW_NUMBER = "%3d "
        private const val BLACK_STONE = '●'
        private const val WHITE_STONE = '○'
    }
}
