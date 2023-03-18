package view

import domain.board.Board
import domain.stone.Color
import domain.stone.Point
import domain.stone.Stone

object BoardMaker {

    private val ALPHABETS = List(15) { 'A' + it }
    private const val BLACK_STONE = "●"
    private const val WHITE_STONE = "◎"
    private const val END = 14
    private const val NUMBER_OF_HORIZONTAL_LINE = 2
    private const val START = 0
    private const val END_STANDARD_OF_VIEW = 15
    private const val PADDING_LENGTH = 4

    private fun Color.printStone(): String {
        return when (this) {
            Color.BLACK -> BLACK_STONE
            Color.WHITE -> WHITE_STONE
        }
    }

    private fun makeBoardBottom(): String {
        val stringBuilder = StringBuilder()

        stringBuilder.append("    ")
        ALPHABETS.forEach { alphabet ->
            stringBuilder.append("$alphabet  ")
        }
        stringBuilder.append("\n")

        return stringBuilder.toString()
    }

    private fun makeBoardHorizontalLine(board: Board, curX: Int, curY: Int): String {
        val stringBuilder = StringBuilder()

        board.placedStones.find { stone: Stone -> stone.point == Point(curX, curY) }?.let {
            stringBuilder.append(it.color.printStone())
        } ?: stringBuilder.append(BoardComposition.valueOf(curX, curY).value)

        if (curX != END) {
            repeat(NUMBER_OF_HORIZONTAL_LINE) {
                stringBuilder.append(BoardComposition.CONNECTING_HORIZONTAL.value)
            }
        }

        return stringBuilder.toString()
    }

    fun make(board: Board): String {
        val stringBuilder = StringBuilder()
        for (y in START..END) {
            stringBuilder.append("${END_STANDARD_OF_VIEW - y} ".padStart(PADDING_LENGTH, ' '))
            for (x in START..END) {
                stringBuilder.append(makeBoardHorizontalLine(board, x, END - y))
            }
            stringBuilder.append("\n")
        }
        stringBuilder.append(makeBoardBottom())

        return stringBuilder.toString()
    }
}
