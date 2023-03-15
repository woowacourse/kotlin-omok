data class Position(val horizontalAxis: HorizontalAxis, val verticalAxis: Int) {
    private var empty = true

    fun isEmpty(): Boolean {
        return empty
    }

    fun occupy() {
        empty = false
    }

    companion object {
        val POSITIONS = HorizontalAxis.values().flatMap { horizontalAxis ->
            (1..15).map { verticalAxis ->
                Position(horizontalAxis, verticalAxis)
            }
        }
    }
}
