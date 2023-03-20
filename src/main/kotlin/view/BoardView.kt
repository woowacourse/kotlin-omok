package view

import model.domain.tools.Board
import model.domain.tools.Stone
import model.domain.tools.Stone.BLACK
import model.domain.tools.Stone.EMPTY
import model.domain.tools.Stone.WHITE

object BoardView {

    private const val BLACK_STONE = '●'
    private const val WHITE_STONE = '◌'
    private const val LEFT_INTERVAL = 4
    private const val RIGHT_INTERVAL = 3
    private const val BOARD_SIZE = 15
    private const val WITHOUT_TOP_BOTTOM = BOARD_SIZE - 2
    private const val WITHOUT_TOP = BOARD_SIZE - 1
    private const val BEFORE_BOTTOM = 2
    private const val LINE_UP_CRITERIA = 10

    private val alphabet = List(BOARD_SIZE) { " ${(it + 65).toChar().uppercaseChar()}" }

    private val start = buildString {
        append("┌")
        append("──┬".repeat(WITHOUT_TOP_BOTTOM))
        append("──┐")
    }
    private val middle = buildString {
        append("├")
        append("──┼".repeat(WITHOUT_TOP_BOTTOM))
        append("──┤")
    }
    private val end = buildString {
        append("└")
        append("──┴".repeat(WITHOUT_TOP_BOTTOM))
        append("──┘")
    }
    private val boardLast = buildString {
        append(" ")
        append(" ").repeat(LEFT_INTERVAL)
        alphabet.forEach { append(" $it") }
    }

    private val board = buildList {
        add(" $BOARD_SIZE $start")
        for (row in WITHOUT_TOP downTo BEFORE_BOTTOM) {
            add(getLineCriteria(row))
        }
        add("  1 $end")
        add(boardLast)
    }

    private fun getLineCriteria(rowNumber: Int): String {
        if (rowNumber >= LINE_UP_CRITERIA) return " $rowNumber $middle"
        return "  $rowNumber $middle"
    }

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
