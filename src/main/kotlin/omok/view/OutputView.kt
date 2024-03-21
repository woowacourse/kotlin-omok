package omok.view

import omok.model.board.Board
import omok.model.stone.GoStone
import omok.model.stone.Stone

class OutputView {
    fun printStartGameComment() = println(START_GAME_MESSAGE)

    fun drawBoard(board: Board) {
        // 오목판 그리기
        for (row in UPPER_LENGTH downTo 0) {
            // 왼쪽 번호
            print("${(row + 1).toString().padStart(2)} ")

            // 이제 drawRow를 각 경우에 맞게 호출
            printBoard(board, row)
        }
        printRowValue()
    }

    fun printWinner(stone: GoStone) {
        lineBreak()
        if (stone.stoneType == Stone.BLACK_STONE) {
            println(BLACK_STONE_WIN_MESSAGE)
        } else {
            println(WHITE_STONE_WIN_MESSAGE)
        }
    }

    private fun printBoard(
        board: Board,
        row: Int,
    ) {
        when (row) {
            UPPER_LENGTH -> {
                drawRow(board, row, "┌", "┬", "┐")
            }

            LOWER_ROW -> {
                drawRow(board, row, "└", "┴", "┘")
            }

            else -> {
                drawRow(board, row, "├", "┼", "┤")
            }
        }
    }

    private fun printRowValue() {
        print("   ")
        for (col in ROW_RANGE) {
            print("$col  ")
        }
        println()
    }

    private fun drawRow(
        board: Board,
        row: Int,
        firstSymbol: String,
        middleSymbol: String,
        lastSymbol: String,
    ) {
        printSymbol(board, row, firstSymbol)
        for (col in 1 until UPPER_LENGTH) {
            val stone = board.board[row][col]
            print(if (stone == Stone.NONE) "──$middleSymbol" else "──${stone.value()}")
        }
        printLastSymbol(board, row, lastSymbol)
        println()
    }

    private fun printLastSymbol(
        board: Board,
        row: Int,
        lastSymbol: String,
    ) {
        val stone = board.board[row][UPPER_LENGTH]
        if (stone != Stone.NONE) {
            print("──${stone.value()}")
        } else {
            print("──$lastSymbol")
        }
    }

    private fun printSymbol(
        board: Board,
        row: Int,
        firstSymbol: String,
    ) {
        when {
            board.board[row][MIN_COL] != Stone.NONE -> print(board.board[row][MIN_COL].value())
            board.board[row][UPPER_LENGTH] != Stone.NONE -> print(board.board[row][UPPER_LENGTH].value())
            else -> print(firstSymbol)
        }
    }

    private fun Stone.value() =
        when (this) {
            Stone.BLACK_STONE -> "●"
            Stone.WHITE_STONE -> "○"
            Stone.NONE -> ""
        }

    private fun lineBreak() = println()

    companion object {
        private const val UPPER_LENGTH = 14
        private const val LOWER_ROW = 0
        private const val ROW_START = 'A'
        private const val ROW_END = 'O'
        private const val MIN_COL = 0
        private val ROW_RANGE = ROW_START..ROW_END
        private const val START_GAME_MESSAGE = "오목 게임을 시작합니다."
        private const val BLACK_STONE_WIN_MESSAGE = "흑의 승리입니다."
        private const val WHITE_STONE_WIN_MESSAGE = "백의 승리입니다."
    }
}
