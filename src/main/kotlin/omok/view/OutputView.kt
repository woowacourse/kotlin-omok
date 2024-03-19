package omok.view

import omok.model.Board
import omok.model.Position
import omok.model.Stone

class OutputView {
    fun printBoard(board: Board) {
        Position.INDEX_RANGE.forEach { row ->
            printBoardRowName(row)
            Position.INDEX_RANGE.forEach { col ->
                printBoardAxis(board, row, col)
            }
            println()
        }
        println("    A  B  C  D  E  F  G  H  I  J  K  L  M  N  O")
    }

    private fun printBoardRowName(row: Int) {
        val rowName = (Position.MAX_INDEX - row + 1).toString()
        if (rowName.length == 1) {
            print(" $rowName ")
            return
        }
        print("$rowName ")
    }

    private fun printBoardAxis(board: Board, row: Int, col: Int) {
        val stone = board.find(Position(row, col))
        if (row == Position.MIN_INDEX) {
            when (col) {
                Position.MIN_INDEX -> stone.printBoardSingleAxis(" ●─", " ○─", " ┌─")
                Position.MAX_INDEX -> stone.printBoardSingleAxis("─● ", "─○ ", "─┐ ")
                else -> stone.printBoardSingleAxis("─●─", "─○─", "─┬─")
            }
        } else if (row == Position.MAX_INDEX) {
            when (col) {
                Position.MIN_INDEX -> stone.printBoardSingleAxis(" ●─", " ○─", " └─")
                Position.MAX_INDEX -> stone.printBoardSingleAxis("─● ", "─○ ", "─┘ ")
                else -> stone.printBoardSingleAxis("─●─", "─○─", "─┴─")
            }
        } else {
            when (col) {
                Position.MIN_INDEX -> stone.printBoardSingleAxis(" ●─", " ○─", " ├─")
                Position.MAX_INDEX -> stone.printBoardSingleAxis("─● ", "─○ ", "─┤ ")
                else -> stone.printBoardSingleAxis("─●─", "─○─", "─┼─")
            }
        }
    }

    private fun Stone.printBoardSingleAxis(black: String, white: String, none: String) {
        when (this) {
            Stone.BLACK -> print(black)
            Stone.WHITE -> print(white)
            Stone.NONE -> print(none)
        }
    }
}
