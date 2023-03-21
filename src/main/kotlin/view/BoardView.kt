package view

import model.domain.tools.Board
import model.domain.tools.Stone
import model.domain.tools.Stone.* // ktlint-disable no-wildcard-imports

object BoardView {
    private const val BLACK_STONE = '●'
    private const val WHITE_STONE = '◌'
    private const val LEFT_INTERVAL = 4
    private const val RIGHT_INTERVAL = 3

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
    fun printBoard(omokBoard: Board) {
        omokBoard.system.values.forEachIndexed { row, rowLine ->
            println(printBoardLine(row, rowLine))
        }
        println(board.last())
    }

    private fun printBoardLine(row: Int, rowLine: MutableList<Stone>): String {
        val stringBuilder = StringBuilder(board[row])

        rowLine.forEachIndexed { col, stone ->
            getStoneState(stone)?.let { stoneState ->
                stringBuilder.setCharAt(LEFT_INTERVAL + col * RIGHT_INTERVAL, stoneState)
            }
        }
        return stringBuilder.toString()
    }

    private fun getStoneState(stone: Stone): Char? = when (stone) {
        BLACK -> BLACK_STONE
        WHITE -> WHITE_STONE
        EMPTY -> null
    }
}
