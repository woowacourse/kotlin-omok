import Position.Companion.POSITION_RANGE

data class Stone private constructor(val position: Position) {
    companion object {
        private val STONES = POSITION_RANGE.map { x -> POSITION_RANGE.map { y -> Stone(Position(x, y)) } }

        fun of(x: Int, y: Int) = STONES[x - 1][y - 1]
    }
}
