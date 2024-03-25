package omok.model

data class Point(val x: Int, val y: Int, val boardSize: Int) {
    init {
        require(x in MIN_POINT until boardSize && y in MIN_POINT until boardSize)
    }

    companion object {
        private const val MIN_POINT = 0
    }
}
