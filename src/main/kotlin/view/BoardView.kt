package view

import domain.OmokGame
import domain.Stone
import domain.XCoordinate
import domain.YCoordinate

class BoardView(omokGame: OmokGame) {

    private val boardLines = mutableListOf<MutableList<String>>()

    init {
        for (y in OmokGame.BOARD_SIZE downTo 1) {
            val boardLine = mutableListOf<String>()
            boardLine.add("%3s ".format(y))
            for (x in 1..OmokGame.BOARD_SIZE) {
                when {
                    omokGame.blackStoneIsPlaced(Stone(XCoordinate.of(x), YCoordinate.of(y))) -> boardLine.add(BLACK_STONE)
                    omokGame.whiteStoneIsPlaced(Stone(XCoordinate.of(x), YCoordinate.of(y))) -> boardLine.add(WHITE_STONE)
                    x == 1 && y == OmokGame.BOARD_SIZE -> boardLine.add(LEFT_TOP)
                    x == OmokGame.BOARD_SIZE && y == OmokGame.BOARD_SIZE -> boardLine.add(RIGHT_TOP)
                    x == 1 && y == 1 -> boardLine.add(LEFT_BOTTOM)
                    x == OmokGame.BOARD_SIZE && y == 1 -> boardLine.add(RIGHT_BOTTOM)
                    x == 1 -> boardLine.add(LEFT)
                    x == OmokGame.BOARD_SIZE -> boardLine.add(RIGHT)
                    y == OmokGame.BOARD_SIZE -> boardLine.add(TOP)
                    y == 1 -> boardLine.add(BOTTOM)
                    else -> boardLine.add(MIDDLE)
                }
                if (x != OmokGame.BOARD_SIZE) boardLine.add("──")
            }
            boardLines.add(boardLine)
        }
        boardLines.add(mutableListOf("    " + ('A' until 'A' + OmokGame.BOARD_SIZE).joinToString("") { "$it  " }))
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
        private const val WHITE_STONE = "◌"

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
