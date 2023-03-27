package omok.domain

data class Position(val horizontalAxis: HorizontalAxis, val verticalAxis: Int) {
    private var empty = true

    init {
        require(horizontalAxis in HorizontalAxis.values()) { HORIZONTAL_AXIS_ERROR_MSG }
        require(verticalAxis in MIN_VERTICAL_AXIS..MAX_VERTICAL_AXIS) { VERTICAL_AXIS_ERROR_MSG }
    }

    fun isEmpty(): Boolean = empty

    fun occupy() {
        empty = false
    }

    companion object {
        const val MIN_VERTICAL_AXIS = 1
        const val MAX_VERTICAL_AXIS = 15
        private const val MIN_HORIZONTAL_AXIS = "A"
        private const val MAX_HORIZONTAL_AXIS = "O"
        private const val HORIZONTAL_AXIS_ERROR_MSG = "가로 축은 ${MIN_HORIZONTAL_AXIS}와 $MAX_HORIZONTAL_AXIS 사이 입니다."
        private const val VERTICAL_AXIS_ERROR_MSG = "세로 축은 ${MIN_VERTICAL_AXIS}과 $MAX_VERTICAL_AXIS 사이 입니다."
    }
}
