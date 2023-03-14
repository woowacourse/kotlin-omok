data class Position(val x: Int, val y: Int) {
    init {
        require(x in MIN_BOUND..MAX_BOUND) { X_OUT_OF_RANGE_ERROR_MESSAGE }
        require(y in MIN_BOUND..MAX_BOUND) { Y_OUT_OF_RANGE_ERROR_MESSAGE }
    }

    companion object {
        const val MIN_BOUND = 1
        const val MAX_BOUND = 15
        private const val X_OUT_OF_RANGE_ERROR_MESSAGE = "x의 범위는 ${MIN_BOUND}부터 ${MAX_BOUND}입니다"
        private const val Y_OUT_OF_RANGE_ERROR_MESSAGE = "y의 범위는 ${MIN_BOUND}부터 ${MAX_BOUND}입니다"
    }
}
