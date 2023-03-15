package omok.domain

data class Position(val horizontalAxis: HorizontalAxis, val verticalAxis: Int) {
    init {
        require(horizontalAxis in HorizontalAxis.values()) { HORIZONTAL_AXIS_ERROR_MSG }
        require(verticalAxis in 1..15) { VERTICAL_AXIS_ERROR_MSG }
    }

    private var empty = true

    fun isEmpty(): Boolean {
        return empty
    }

    fun occupy() {
        empty = false
    }

    companion object {
        private const val HORIZONTAL_AXIS_ERROR_MSG = "가로축은 A와 O 사이 입니다."
        private const val VERTICAL_AXIS_ERROR_MSG = "세로축은 A와 O 사이 입니다."
        val POSITIONS = HorizontalAxis.values().flatMap { horizontalAxis ->
            (1..15).map { verticalAxis ->
                Position(horizontalAxis, verticalAxis)
            }
        }
    }
}
