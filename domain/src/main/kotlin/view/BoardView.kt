package view

import domain.OmokGame
import domain.stone.Point
import domain.stone.Stone

class BoardView(omokGame: OmokGame) {

    private val boardLines by lazy {
        (OmokGame.BOARD_SIZE downTo 1).joinToString(
            separator = "\n",
            postfix = createBoardColumnNames(),
        ) { createBoardLine(it, omokGame) }
    }

    private fun createBoardColumnNames(): String =
        "\n    " + ('A' until 'A' + OmokGame.BOARD_SIZE).joinToString("  ") { "$it" }

    private fun createBoardLine(y: Int, omokGame: OmokGame): String =
        (1..OmokGame.BOARD_SIZE).joinToString(
            prefix = "%3s ".format(y),
            separator = HORIZONTAL_LINE,
        ) { Point(it, y).getMark(omokGame) }

    private fun Point.getMark(omokGame: OmokGame): String =
        when {
            this.isWhereBlackStoneIsPlaced(omokGame) -> BLACK_STONE
            this.isWhereWhiteStoneIsPlaced(omokGame) -> WHITE_STONE
            this.isLeftTopCornerOfBoard() -> LEFT_TOP
            this.isRightTopCornerOfBoard() -> RIGHT_TOP
            this.isLeftBottomCornerOfBoard() -> LEFT_BOTTOM
            this.isRightBottomCornerOfBoard() -> RIGHT_BOTTOM
            this.isTopCornerOfBoard() -> TOP
            this.isBottomCornerOfBoard() -> BOTTOM
            this.isLeftCornerOfBoard() -> LEFT
            this.isRightCornerOfBoard() -> RIGHT
            else -> MIDDLE
        }

    private fun Point.isWhereBlackStoneIsPlaced(omokGame: OmokGame): Boolean = omokGame.blackStoneIsPlaced(Stone(this))
    private fun Point.isWhereWhiteStoneIsPlaced(omokGame: OmokGame): Boolean = omokGame.whiteStoneIsPlaced(Stone(this))
    private fun Point.isLeftTopCornerOfBoard(): Boolean = x == 'A' && y == OmokGame.BOARD_SIZE
    private fun Point.isRightTopCornerOfBoard(): Boolean =
        x == 'A' + OmokGame.BOARD_SIZE - 1 && y == OmokGame.BOARD_SIZE

    private fun Point.isLeftBottomCornerOfBoard(): Boolean = x == 'A' && y == 1
    private fun Point.isRightBottomCornerOfBoard(): Boolean = x == 'A' + OmokGame.BOARD_SIZE - 1 && y == 1
    private fun Point.isTopCornerOfBoard(): Boolean = y == OmokGame.BOARD_SIZE
    private fun Point.isBottomCornerOfBoard(): Boolean = y == 1
    private fun Point.isLeftCornerOfBoard(): Boolean = x == 'A'
    private fun Point.isRightCornerOfBoard(): Boolean = x == 'A' + OmokGame.BOARD_SIZE - 1

    override fun toString(): String = boardLines

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
        private const val HORIZONTAL_LINE = "──"
    }
}
