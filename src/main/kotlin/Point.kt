data class Point(val x: Int, val y: Int) {
    init {
        require(x in AXIS_RANGE) { ERROR_X_AXIS_RANGE }
        require(y in AXIS_RANGE) { ERROR_Y_AXIS_RANGE }
    }
    
    companion object{
        private const val ERROR_X_AXIS_RANGE = "[ERROR] X좌표의 값은 1에서 15사이여야 합니다."
        private const val ERROR_Y_AXIS_RANGE = "[ERROR] Y좌표의 값은 1에서 15사이여야 합니다."
        private val AXIS_RANGE = 1..15
    }
}