package omok.view

import omok.model.Color
import omok.model.Color.BLACK
import omok.model.Color.WHITE

class OutputView {
    fun showGameStartMessage()  {
        println(MESSAGE_GAME_START)
    }

    fun showCurrentBoard(board: Array<Array<Color?>>) {
        for (i in 1..<board.size) {
            print(ROW_INDICATOR.format(BOARD_SIZE - i))
            showSingleRow(board, i)
            println(DIVIDER_ROW)
        }
        println(COLUMN_INDICATOR)
    }

    private fun showSingleRow(
        board: Array<Array<Color?>>,
        i: Int,
    ) {
        for (j in 1..<board[i].size) {
            print(getSingleCell(board[i][j]))
            if (j < board[i].size - 1) print(DIVIDER_ROW)
        }
    }

    private fun getSingleCell(stone: Color?) =
        when (stone) {
            null -> SIGNATURE_BLANK
            BLACK -> SIGNATURE_BLACK
            WHITE -> SIGNATURE_WHITE
        }

    companion object {
        private const val COLUMN_INDICATOR = "   A B C D E F G H I J K L M N O"
        private const val ROW_INDICATOR = "%2d─"
        private const val SIGNATURE_BLANK = "┼"
        private const val SIGNATURE_BLACK = "●"
        private const val SIGNATURE_WHITE = "○"
        private const val DIVIDER_ROW = "─"
        private const val BOARD_SIZE = 16
        private const val MESSAGE_GAME_START = "오목 게임을 시작합니다."
    }
}
