package omok.model.stone

import omok.model.Board.Companion.MAX_BOARD_HEIGHT
import omok.model.Board.Companion.MAX_BOARD_WIDTH
import omok.model.Board.Companion.MIN_BOARD_HEIGHT
import omok.model.Board.Companion.MIN_BOARD_WIDTH

data class Point(
    val row: Int,
    val col: Int,
) {
    init {
        require(row in MIN_BOARD_HEIGHT..MAX_BOARD_HEIGHT && col in MIN_BOARD_WIDTH..MAX_BOARD_WIDTH) { ERROR_MESSAGE_OUT_OF_BOARD }
    }

    companion object {
        private const val ERROR_MESSAGE_OUT_OF_BOARD = "오목판 범위를 벗어났습니다."
    }
}
