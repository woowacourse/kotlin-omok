package omok.model

data class Position(val format: String, val coordinate: Coordinate) {
    companion object {
        fun from(input: String): Position {
            return Position(input, Coordinate.from(input))
        }
    }
}
