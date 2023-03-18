package omok.domain

data class Position(val horizontalAxis: HorizontalAxis, val verticalAxis: Int) {
    private var empty = true

    init {
        require(horizontalAxis in HorizontalAxis.values()) { HORIZONTAL_AXIS_ERROR_MSG.format(MIN_HORIZONTAL_AXIS, MAX_HORIZONTAL_AXIS) }
        require(verticalAxis in MIN_VERTICAL_AXIS..MAX_VERTICAL_AXIS) { VERTICAL_AXIS_ERROR_MSG.format(MIN_VERTICAL_AXIS, MAX_VERTICAL_AXIS) }
    }

    fun isEmpty(): Boolean = empty

    fun occupy() {
        empty = false
    }

    companion object {
        private const val MIN_VERTICAL_AXIS = 1
        private const val MAX_VERTICAL_AXIS = 15
        private const val MIN_HORIZONTAL_AXIS = "A"
        private const val MAX_HORIZONTAL_AXIS = "O"
        private const val HORIZONTAL_AXIS_ERROR_MSG = "가로축은 %s와 %s 사이입니다."
        private const val VERTICAL_AXIS_ERROR_MSG = "세로축은 %d과 %d 사이 입니다."
        val POSITIONS = HorizontalAxis.values().flatMap { horizontalAxis ->
            (MIN_VERTICAL_AXIS..MAX_VERTICAL_AXIS).map { verticalAxis ->
                Position(horizontalAxis, verticalAxis)
            }
        }
    }
}
