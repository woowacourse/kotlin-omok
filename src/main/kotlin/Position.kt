data class Position(val x: Int, val y: Int) {
    init {
        require(x in POSITION_RANGE) { X_OUT_OF_RANGE_ERROR_MESSAGE }
        require(y in POSITION_RANGE) { Y_OUT_OF_RANGE_ERROR_MESSAGE }
    }

    companion object {
        private const val MIN_BOUND = 1
        private const val MAX_BOUND = 15
        val POSITION_RANGE = (MIN_BOUND..MAX_BOUND)
        private const val X_OUT_OF_RANGE_ERROR_MESSAGE = "x의 범위는 ${MIN_BOUND}부터 ${MAX_BOUND}입니다"
        private const val Y_OUT_OF_RANGE_ERROR_MESSAGE = "y의 범위는 ${MIN_BOUND}부터 ${MAX_BOUND}입니다"
    }
}
