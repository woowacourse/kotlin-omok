package omok.model.position

data class Direction(val rowDirection: Int, val columnDirection: Int) {
    operator fun unaryMinus(): Direction {
        return Direction(-rowDirection, -columnDirection)
    }

    companion object {
        private val horizontalDirection = Direction(0, 1)
        private val verticalDirection = Direction(1, 0)
        private val upwardDirection = Direction(1, 1)
        private val downwardDirection = Direction(1, -1)
        val types: List<Direction> = listOf(horizontalDirection, verticalDirection, upwardDirection, downwardDirection)
    }
}
