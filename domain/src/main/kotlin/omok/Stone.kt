package omok

class Stone(val position: Position) {
    fun findPosition(value: Position) = (position == value)

    fun toViewPosition(): Int {
        return (position.horizontalAxis.axis - 1) * 15 + (position.verticalAxis - 1)
    }
}
