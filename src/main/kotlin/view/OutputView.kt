package view

import domain.Board
import domain.YCoordinate

object OutputView {

    private const val BLACK_STONE = "●"
    private const val WHITE_STONE = "○"

    fun printBoard(board: Board) {
        return println(BoardView(board.blackStones, board.whiteStones))
    }

    private fun createBoard(): String {
        val sb = StringBuilder()
        with(sb) {
            this.append((YCoordinate.Y_MAX_RANGE downTo YCoordinate.Y_MIN_RANGE).map {
                "%3s \n".format(YCoordinate.Y_MAX_RANGE)
            })
        }
        return sb.toString()
    }
}
