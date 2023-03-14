data class Position(val horizontalAxis: HorizontalAxis, val verticalAxis: Int) {
    private val empty = true

    fun isEmpty(): Boolean {
        return empty
    }

    companion object {
        val POSITIONS = HorizontalAxis.values().flatMap { horizontalAxis ->
            (1..15).map { verticalAxis ->
                Position(horizontalAxis, verticalAxis)
            }
        }
    }
}
