package omok.view

import omok.model.Board
import omok.model.GoStone
import omok.model.Stone

class OutputView {
    fun printStartGameComment() = println("오목 게임을 시작합니다.")

    fun drawBoard(board: Board) {
        // 오목판 그리기
        for (row in BOARD_SIZE - 1 downTo 0) {
            // 왼쪽 번호
            print("${(row + 1).toString().padStart(2)} ")

            // 이제 drawRow를 각 경우에 맞게 호출
            printBoard(board, row)
        }
        printAlphabet()
    }

    fun printWinner(stone: GoStone) {
        lineBreak()
        if (stone.stoneType == Stone.BLACK_STONE) {
            println("흑의 승리입니다.")
        } else {
            println("백의 승리입니다.")
        }
    }

    private fun printBoard(
        board: Board,
        row: Int,
    ) {
        when (row) {
            BOARD_SIZE - 1 -> {
                drawRow(board, row, "┌", "┬", "┐")
            }

            0 -> {
                drawRow(board, row, "└", "┴", "┘")
            }

            else -> {
                drawRow(board, row, "├", "┼", "┤")
            }
        }
    }

    private fun printAlphabet() {
        // 하단 알파벳
        print("   ")
        for (col in 'A'..'O') {
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
        for (col in 1 until BOARD_SIZE - 1) {
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
        val stone = board.board[row][BOARD_SIZE - 1]
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
        if (board.board[row][0] != Stone.NONE) {
            print("${board.board[row][0].value()}")
        } else if (board.board[row][BOARD_SIZE - 1] != Stone.NONE) {
            print("${board.board[row][BOARD_SIZE - 1].value()}")
        } else {
            print(firstSymbol)
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
        private const val BOARD_SIZE = 15
    }
}
