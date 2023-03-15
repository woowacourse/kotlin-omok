package view

import domain.Board
import domain.Stone
import domain.XCoordinate
import domain.YCoordinate

class BoardView(board: Board) {

    private val boardLines = mutableListOf<MutableList<String>>()

    init {
        for (y in YCoordinate.Y_MAX_RANGE downTo YCoordinate.Y_MIN_RANGE) {
            val boardLine = mutableListOf<String>()
            boardLine.add("%3s ".format(y))
            for (x in XCoordinate.X_MIN_RANGE..XCoordinate.X_MAX_RANGE) {
                when {
                    board.blackStoneIsPlaced(Stone(XCoordinate.of(x), YCoordinate.of(y))) -> boardLine.add(BLACK_STONE)
                    board.whiteStoneIsPlaced(Stone(XCoordinate.of(x), YCoordinate.of(y))) -> boardLine.add(WHITE_STONE)
                    x == XCoordinate.X_MIN_RANGE && y == YCoordinate.Y_MAX_RANGE -> boardLine.add(LEFT_TOP)
                    x == XCoordinate.X_MAX_RANGE && y == YCoordinate.Y_MAX_RANGE -> boardLine.add(RIGHT_TOP)
                    x == XCoordinate.X_MIN_RANGE && y == YCoordinate.Y_MIN_RANGE -> boardLine.add(LEFT_BOTTOM)
                    x == XCoordinate.X_MAX_RANGE && y == YCoordinate.Y_MIN_RANGE -> boardLine.add(RIGHT_BOTTOM)
                    x == XCoordinate.X_MIN_RANGE -> boardLine.add(LEFT)
                    x == XCoordinate.X_MAX_RANGE -> boardLine.add(RIGHT)
                    y == YCoordinate.Y_MAX_RANGE -> boardLine.add(TOP)
                    y == YCoordinate.Y_MIN_RANGE -> boardLine.add(BOTTOM)
                    else -> boardLine.add(MIDDLE)
                }
                if (x != XCoordinate.X_MAX_RANGE) boardLine.add("──")
            }
            boardLines.add(boardLine)
        }
        boardLines.add(mutableListOf("    " + (XCoordinate.X_MIN_RANGE..XCoordinate.X_MAX_RANGE).joinToString("") { "$it  " }))
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()

        boardLines.forEach {
            stringBuilder.append(it.joinToString(""))
            stringBuilder.append("\n")
        }
        return stringBuilder.toString()
    }

    companion object {
        private const val BLACK_STONE = "●"
        private const val WHITE_STONE = "○"

        private const val LEFT_TOP = "┌"
        private const val TOP = "┬"
        private const val RIGHT_TOP = "┐"
        private const val LEFT = "├"
        private const val MIDDLE = "┼"
        private const val RIGHT = "┤"
        private const val LEFT_BOTTOM = "└"
        private const val BOTTOM = "┴"
        private const val RIGHT_BOTTOM = "┘"
    }
}
