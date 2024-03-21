package omok.model

data class Point(val x: Int, val y: Int) {
    init {
        require(x in MIN_POINT until MAX_POINT && y in MIN_POINT until MAX_POINT)
    }

    companion object {
        private const val MIN_POINT = 0
        private const val MAX_POINT = 15
    }
}
