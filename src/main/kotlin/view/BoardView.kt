package view

import model.domain.Board
import model.domain.Stone

class BoardView {
    fun printBoard(omokBoard: Board) {
        omokBoard.system.values.forEachIndexed { row, rowLine ->
            println(printBoardLine(row, rowLine))
        }
        println(board.last())
    }

    private fun printBoardLine(row: Int, rowLine: MutableList<Stone>): String {
        val stringBuilder = StringBuilder(board[row])

        rowLine.forEachIndexed { col, stone ->
            getStoneState(stone)?.let { stringBuilder.setCharAt(4 + col * 3, it) }
        }
        return stringBuilder.toString()
    }

    private fun getStoneState(stone: Stone): Char? = when (stone) {
        Stone.BLACK -> '●'
        Stone.WHITE -> '◌'
        Stone.EMPTY -> null
    }

    companion object {
        private val board = listOf(
            " 15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐",
            " 14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
            " 13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
            " 12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
            " 11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
            " 10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
            "  9 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
            "  8 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
            "  7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
            "  6 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
            "  5 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
            "  4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
            "  3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
            "  2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
            "  1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘",
            "    A  B  C  D  E  F  G  H  I  J  K  L  M  N  O",
        )
    }
}
