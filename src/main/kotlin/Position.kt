class Position(val horizontalAxis: HorizontalAxis, val verticalAxis: Int) {
    val empty = true

    fun isPlaceable(): Boolean {
        return empty
    }
}
