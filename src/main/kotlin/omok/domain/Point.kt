package omok.domain

data class Point(
    val x: Int,
    val y: Int,
) {
    companion object {
        private const val MIN_POSITION = 0
        private const val ERROR_INVALID_POSITION = "[ERROR] 바둑판의 크기는 %dx%d입니다."

        fun create(
            x: Int,
            y: Int,
            boardSize: Int,
        ): Point {
            require(x in MIN_POSITION..<boardSize) {
                ERROR_INVALID_POSITION.format(boardSize, boardSize)
            }
            require(y in MIN_POSITION..<boardSize) {
                ERROR_INVALID_POSITION.format(boardSize, boardSize)
            }
            return Point(x, y)
        }
    }
}
