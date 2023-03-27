package view

import domain.Board
import domain.stone.Point
import domain.stone.Stone
import domain.stone.Team

class BoardView(board: Board) {

    private val boardLines by lazy {
        (Board.BOARD_SIZE downTo 1).joinToString(
            separator = "\n",
            postfix = createBoardColumnNames(),
        ) { createBoardLine(it, board) }
    }

    private fun createBoardColumnNames(): String =
        "\n    " + ('A' until 'A' + Board.BOARD_SIZE).joinToString("  ") { "$it" }

    private fun createBoardLine(y: Int, board: Board): String =
        (1..Board.BOARD_SIZE).joinToString(
            prefix = "%3s ".format(y),
            separator = HORIZONTAL_LINE,
        ) { Point(it, y).getMark(board) }

    private fun Point.getMark(board: Board): String =
        when {
            this.isWhereBlackStoneIsPlaced(board) -> BLACK_STONE
            this.isWhereWhiteStoneIsPlaced(board) -> WHITE_STONE
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

    private fun Point.isWhereBlackStoneIsPlaced(board: Board): Boolean =
        board.isPlaced(Team.BLACK, Stone(this))

    private fun Point.isWhereWhiteStoneIsPlaced(board: Board): Boolean =
        board.isPlaced(Team.WHITE, Stone(this))

    private fun Point.isLeftTopCornerOfBoard(): Boolean = x == 'A' && y == Board.BOARD_SIZE
    private fun Point.isRightTopCornerOfBoard(): Boolean =
        x == 'A' + Board.BOARD_SIZE - 1 && y == Board.BOARD_SIZE

    private fun Point.isLeftBottomCornerOfBoard(): Boolean = x == 'A' && y == 1
    private fun Point.isRightBottomCornerOfBoard(): Boolean =
        x == 'A' + Board.BOARD_SIZE - 1 && y == 1

    private fun Point.isTopCornerOfBoard(): Boolean = y == Board.BOARD_SIZE
    private fun Point.isBottomCornerOfBoard(): Boolean = y == 1
    private fun Point.isLeftCornerOfBoard(): Boolean = x == 'A'
    private fun Point.isRightCornerOfBoard(): Boolean = x == 'A' + Board.BOARD_SIZE - 1

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
