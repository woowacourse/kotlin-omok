package omok.model

data class Point(val x: Int, val y: Int) {
    init {
        require(x in MIN_POINT until Board.BOARD_SIZE && y in MIN_POINT until Board.BOARD_SIZE)
    }

    companion object {
        private const val MIN_POINT = 0
    }
}
