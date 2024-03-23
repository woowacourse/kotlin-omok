package omok.view

import omok.model.Board
import omok.model.Position
import omok.model.Stone

class OutputView {
    fun printInitialGuide(board: Board) {
        println(INITIAL_GUIDE_MESSAGE)
        printBoard(board)
    }

    fun printBoard(board: Board) {
        val boardSizeRange = board.startIndex..board.endIndex
        lineBreak()
        boardSizeRange.forEach { row ->
            printBoardRowName(board, row)
            boardSizeRange.forEach { col ->
                printBoardAxis(board, row, col)
            }
            lineBreak()
        }
        printBoardColName(boardSizeRange)
        lineBreak()
    }

    private fun printBoardRowName(
        board: Board,
        row: Int,
    ) {
        val rowName = (board.endIndex - row + 1).toString()
        if (rowName.length == 1) {
            print(" $rowName ")
            return
        }
        print("$rowName ")
    }

    private fun printBoardAxis(
        board: Board,
        row: Int,
        col: Int,
    ) {
        val stone = board.findOrNull(Position(row, col)) ?: return
        if (row == board.startIndex) {
            when (col) {
                board.startIndex -> stone.printBoardSingleAxis(" ${BLACK_STONE}─", " ${WHITE_STONE}─", " ┌─")
                board.endIndex -> stone.printBoardSingleAxis("─${BLACK_STONE} ", "─${WHITE_STONE} ", "─┐ ")
                else -> stone.printBoardSingleAxis("─${BLACK_STONE}─", "─${WHITE_STONE}─", "─┬─")
            }
        } else if (row == board.endIndex) {
            when (col) {
                board.startIndex -> stone.printBoardSingleAxis(" ${BLACK_STONE}─", " ${WHITE_STONE}─", " └─")
                board.endIndex -> stone.printBoardSingleAxis("─${BLACK_STONE} ", "─${WHITE_STONE} ", "─┘ ")
                else -> stone.printBoardSingleAxis("─${BLACK_STONE}─", "─${WHITE_STONE}─", "─┴─")
            }
        } else {
            when (col) {
                board.startIndex -> stone.printBoardSingleAxis(" ${BLACK_STONE}─", " ${WHITE_STONE}─", " ├─")
                board.endIndex -> stone.printBoardSingleAxis("─${BLACK_STONE} ", "─${WHITE_STONE} ", "─┤ ")
                else -> stone.printBoardSingleAxis("─${BLACK_STONE}─", "─${WHITE_STONE}─", "─┼─")
            }
        }
    }

    private fun lineBreak() = println()

    private fun Stone.printBoardSingleAxis(
        black: String,
        white: String,
        none: String,
    ) {
        when (this) {
            Stone.BLACK -> print(black)
            Stone.WHITE -> print(white)
            Stone.NONE -> print(none)
        }
    }

    private fun printBoardColName(boardSizeRange: IntRange) {
        println(boardSizeRange.joinToString(prefix = "    ", separator = "  ") { (it + 'A'.code).toChar().toString() })
    }

    fun printWinner(stone: Stone) {
        println(WINNER_MESSAGE.format(stone.output()))
    }

    companion object {
        private const val INITIAL_GUIDE_MESSAGE = "오목 게임을 시작합니다."
        private const val WINNER_MESSAGE = "우승은 %s🎉 입니다"
        private const val BLACK_STONE = "●"
        private const val WHITE_STONE = "○"
    }
}
