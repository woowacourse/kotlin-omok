package omok

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

    override fun toString(): String {
        return horizontalAxis.toString() + verticalAxis.toString()
    }

    companion object {
        private const val MIN_VERTICAL_AXIS = 1
        private const val MAX_VERTICAL_AXIS = 15
        private const val HORIZONTAL_AXIS_ERROR_MSG = "가로축은 A와 O 사이 입니다."
        private const val VERTICAL_AXIS_ERROR_MSG = "세로축은 1과 15 사이 입니다."
        val POSITIONS = HorizontalAxis.values().flatMap { horizontalAxis ->
            (MIN_VERTICAL_AXIS..MAX_VERTICAL_AXIS).map { verticalAxis ->
                Position(horizontalAxis, verticalAxis)
            }
        }
    }
}
