package omok.view

import omok.model.Board
import omok.model.FinishType
import omok.model.Position
import omok.model.Stone

class OutputView {
    fun printInitialGuide(board: Board) {
        println(INITIAL_GUIDE_MESSAGE)
        printBoard(board)
    }

    fun printBoard(board: Board) {
        val boardSizeRange = 0..<board.size
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
        val rowName = (board.size - row).toString()
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
        if (row == 0) {
            when (col) {
                0 -> stone.printBoardSingleAxis(" ${BLACK_STONE}─", " ${WHITE_STONE}─", " ┌─")
                board.size - 1 -> stone.printBoardSingleAxis("─${BLACK_STONE} ", "─${WHITE_STONE} ", "─┐ ")
                else -> stone.printBoardSingleAxis("─${BLACK_STONE}─", "─${WHITE_STONE}─", "─┬─")
            }
        } else if (row == board.size - 1) {
            when (col) {
                0 -> stone.printBoardSingleAxis(" ${BLACK_STONE}─", " ${WHITE_STONE}─", " └─")
                board.size - 1 -> stone.printBoardSingleAxis("─${BLACK_STONE} ", "─${WHITE_STONE} ", "─┘ ")
                else -> stone.printBoardSingleAxis("─${BLACK_STONE}─", "─${WHITE_STONE}─", "─┴─")
            }
        } else {
            when (col) {
                0 -> stone.printBoardSingleAxis(" ${BLACK_STONE}─", " ${WHITE_STONE}─", " ├─")
                board.size - 1 -> stone.printBoardSingleAxis("─${BLACK_STONE} ", "─${WHITE_STONE} ", "─┤ ")
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

    fun printResult(finishType: FinishType) {
        if (finishType == FinishType.DRAW) {
            println("무승부입니다.")
            return
        }
        println(WINNER_MESSAGE.format(finishType.stone.output()))
    }

    companion object {
        private const val INITIAL_GUIDE_MESSAGE = "오목 게임을 시작합니다."
        private const val WINNER_MESSAGE = "우승은 %s🎉 입니다"
        private const val BLACK_STONE = "●"
        private const val WHITE_STONE = "○"
    }
}
