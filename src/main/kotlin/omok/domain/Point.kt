package omok.domain

import omok.domain.Board.Companion.DEFAULT_BOARD_SIZE

data class Point(
    val x: Int,
    val y: Int,
    val boardSize: Int = DEFAULT_BOARD_SIZE
) {
    companion object {
        private const val ERROR_INVALID_POSITION = "[ERROR] 바둑판의 크기는 %dx%d입니다."

        fun validate(x: Int, y: Int, boardSize: Int) {
            require(x in 0 until boardSize && y in 0 until boardSize) {
                ERROR_INVALID_POSITION.format(boardSize, boardSize)
            }
        }
    }
}