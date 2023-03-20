package domain.stone

data class Point(val x: Int, val y: Int) {

    init {
        require(x in POINT_RANGE && y in POINT_RANGE) {
            ERROR_RANGE
        }
    }

    companion object {
        private val POINT_RANGE = (1..15)
        private const val ERROR_RANGE = "[ERROR] 행과 열의 범위는 1이상 15이하입니다."
    }
}
