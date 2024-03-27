package omok.model.position

data class Direction(val row: Row, val column: Column) {
    operator fun unaryMinus(): Direction {
        return Direction(Row(-row.value), Column(-column.value))
    }

    companion object {
        private val horizontalDirection = Direction(Row(0), Column(1))
        private val verticalDirection = Direction(Row(1), Column(0))
        private val upwardDirection = Direction(Row(1), Column(1))
        private val downwardDirection = Direction(Row(1), Column(-1))
        val types: List<Direction> = listOf(horizontalDirection, verticalDirection, upwardDirection, downwardDirection)
    }
}
