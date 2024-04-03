package omok.view

import omok.model.board.Board
import omok.model.stone.StoneType

class ProgressView {
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

    fun printHintMessage(message: String) {
        println(message)
        lineBreak()
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
        for (column in ROW_RANGE) {
            print("$column  ")
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
        for (column in 1 until UPPER_LENGTH) {
            val stone = board.board[row][column]
            print(if (stone == StoneType.NONE) "──$middleSymbol" else "──${stone.value()}")
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
        if (stone != StoneType.NONE) {
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
            board.board[row][MIN_COLUMN] != StoneType.NONE -> print(board.board[row][MIN_COLUMN].value())
            board.board[row][UPPER_LENGTH] != StoneType.NONE -> print(board.board[row][UPPER_LENGTH].value())
            else -> print(firstSymbol)
        }
    }

    private fun StoneType.value() =
        when (this) {
            StoneType.BLACK_STONE -> "●"
            StoneType.WHITE_STONE -> "○"
            StoneType.NONE -> ""
        }

    private fun lineBreak() = println()

    companion object {
        private const val UPPER_LENGTH = 14
        private const val LOWER_ROW = 0
        private const val ROW_START = 'A'
        private const val ROW_END = 'O'
        private const val MIN_COLUMN = 0
        private val ROW_RANGE = ROW_START..ROW_END
        private const val START_GAME_MESSAGE = "오목 게임을 시작합니다."
    }
}
