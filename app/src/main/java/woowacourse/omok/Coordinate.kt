package woowacourse.omok

data class Coordinate(val x: Int, val y: Int) {
    init {
        x.validateRange()
        y.validateRange()
    }

    private fun Int.validateRange() {
        require(this in MINIMUM_RANGE..MAXIMUM_RANGE) { ERROR_INVALID_POSITION }
    }

    companion object {
        private const val MINIMUM_RANGE = 0
        private const val MAXIMUM_RANGE = 14
        const val ERROR_INVALID_POSITION = "유효하지 않은 위치입니다."
    }
}
