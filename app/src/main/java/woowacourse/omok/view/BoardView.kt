package omok.view

import omok.model.board.Board
import omok.model.board.Position
import omok.model.board.Stone

class BoardView {
    fun print(board: Board) {
        val boardSizeRange = 0..<board.size
        lineBreak()
        boardSizeRange.forEach { row ->
            printBoardRowName(board, row)
            boardSizeRange.forEach { col ->
                printBoardAxis(board, Position(row, col))
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
        position: Position,
    ) {
        if (position.row == 0) {
            board.printBoardColAxis(position, " ┌─", "─┐ ", "─┬─")
        } else if (position.row == board.size - 1) {
            board.printBoardColAxis(position, " └─", "─┘ ", "─┴─")
        } else {
            board.printBoardColAxis(position, " ├─", "─┤ ", "─┼─")
        }
    }

    private fun Board.printBoardColAxis(
        position: Position,
        firstCol: String,
        endCol: String,
        middleCol: String,
    ) {
        val stone = find(position)
        when (position.col) {
            0 -> stone.printBoardSingleAxis(" ${BLACK_STONE}─", " ${WHITE_STONE}─", firstCol)
            size - 1 -> stone.printBoardSingleAxis("─${BLACK_STONE} ", "─${WHITE_STONE} ", endCol)
            else -> stone.printBoardSingleAxis("─${BLACK_STONE}─", "─${WHITE_STONE}─", middleCol)
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

    companion object {
        private const val BLACK_STONE = "●"
        private const val WHITE_STONE = "○"
    }
}
