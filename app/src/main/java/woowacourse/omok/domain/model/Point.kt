package omok.model

data class Point(val x: Int, val y: Int) {
    init {
        require(x in MIN_POINT until MAX_POINT && y in MIN_POINT until MAX_POINT) { MESSAGE_INVALID_POINT_INPUT }
    }

    companion object {
        private const val MIN_POINT = 0
        private const val MAX_POINT = 15
        private const val MESSAGE_INVALID_POINT_INPUT = "잘못된 위치 좌표입니다. 재입력 해주세요."
    }
}
