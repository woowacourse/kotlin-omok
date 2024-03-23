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
        lineBreak()
        Position.INDEX_RANGE.forEach { row ->
            printBoardRowName(row)
            Position.INDEX_RANGE.forEach { col ->
                printBoardAxis(board, row, col)
            }
            lineBreak()
        }
        println("    A  B  C  D  E  F  G  H  I  J  K  L  M  N  O")
        lineBreak()
    }

    private fun printBoardRowName(row: Int) {
        val rowName = (Position.MAX_INDEX - row + 1).toString()
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
        val stone = board.find(Position(row, col))
        if (row == Position.MIN_INDEX) {
            when (col) {
                Position.MIN_INDEX -> stone.printBoardSingleAxis(" ${BLACK_STONE}─", " ${WHITE_STONE}─", " ┌─")
                Position.MAX_INDEX -> stone.printBoardSingleAxis("─${BLACK_STONE} ", "─${WHITE_STONE} ", "─┐ ")
                else -> stone.printBoardSingleAxis("─${BLACK_STONE}─", "─${WHITE_STONE}─", "─┬─")
            }
        } else if (row == Position.MAX_INDEX) {
            when (col) {
                Position.MIN_INDEX -> stone.printBoardSingleAxis(" ${BLACK_STONE}─", " ${WHITE_STONE}─", " └─")
                Position.MAX_INDEX -> stone.printBoardSingleAxis("─${BLACK_STONE} ", "─${WHITE_STONE} ", "─┘ ")
                else -> stone.printBoardSingleAxis("─${BLACK_STONE}─", "─${WHITE_STONE}─", "─┴─")
            }
        } else {
            when (col) {
                Position.MIN_INDEX -> stone.printBoardSingleAxis(" ${BLACK_STONE}─", " ${WHITE_STONE}─", " ├─")
                Position.MAX_INDEX -> stone.printBoardSingleAxis("─${BLACK_STONE} ", "─${WHITE_STONE} ", "─┤ ")
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
