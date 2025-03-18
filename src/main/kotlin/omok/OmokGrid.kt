package omok

class OmokGrid {
    val board: List<List<Point>>

    init {
        board =
            List(DEFAULT_SIZE) { row ->
                List(DEFAULT_SIZE) { col ->
                    Point(row + 1, col + 1, StoneState.BLANK)
                }
            }
    }

    companion object {
        private const val DEFAULT_SIZE: Int = 15
    }
}
