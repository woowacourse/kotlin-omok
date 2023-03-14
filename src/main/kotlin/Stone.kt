class Stone private constructor(val position: Position) {
    companion object {
        private val POSITION_RANGE = (Position.MIN_BOUND..Position.MAX_BOUND)
        private val STONES = POSITION_RANGE.map { x -> POSITION_RANGE.map { y -> Stone(Position(x, y)) } }

        fun of(x: Int, y: Int) = STONES[x][y]
    }
}
