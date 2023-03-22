package view

import domain.*
import domain.Board.Companion.MAX_VIEW_X
import domain.Board.Companion.MAX_VIEW_Y
import domain.Board.Companion.MIN_VIEW_X
import domain.Board.Companion.MIN_VIEW_Y
import domain.stone.BlackStone
import domain.stone.WhiteStone

class BoardView(board: Board) {

    private val boardLines = mutableListOf<MutableList<String>>()

    init {
        for (y in MAX_VIEW_Y downTo MIN_VIEW_Y) {
            val boardLine = mutableListOf<String>()
            boardLine.add("%3s ".format(y))
            for (x in MIN_VIEW_X..MAX_VIEW_X) {
                when {
                    board.blackStoneIsPlaced(
                        BlackStone(
                            x.uppercase()[0] - MIN_VIEW_X,
                            y - MIN_VIEW_Y
                        )
                    ) -> boardLine.add(
                        BLACK_STONE
                    )

                    board.whiteStoneIsPlaced(
                        WhiteStone(
                            x.uppercase()[0] - MIN_VIEW_X,
                            y - MIN_VIEW_Y
                        )
                    ) -> boardLine.add(
                        WHITE_STONE
                    )

                    x == MIN_VIEW_X && y == MAX_VIEW_Y -> boardLine.add(LEFT_TOP)
                    x == MAX_VIEW_X && y == MAX_VIEW_Y -> boardLine.add(RIGHT_TOP)
                    x == MIN_VIEW_X && y == MIN_VIEW_Y -> boardLine.add(LEFT_BOTTOM)
                    x == MAX_VIEW_X && y == MIN_VIEW_Y -> boardLine.add(RIGHT_BOTTOM)
                    x == MIN_VIEW_X -> boardLine.add(LEFT)
                    x == MAX_VIEW_X -> boardLine.add(RIGHT)
                    y == MAX_VIEW_Y -> boardLine.add(TOP)
                    y == MIN_VIEW_Y -> boardLine.add(BOTTOM)
                    else -> boardLine.add(MIDDLE)
                }
                if (x != MAX_VIEW_X) boardLine.add("──")
            }
            boardLines.add(boardLine)
        }
        boardLines.add(mutableListOf("    " + (MIN_VIEW_X..MAX_VIEW_X).joinToString("") { "$it  " }))
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
