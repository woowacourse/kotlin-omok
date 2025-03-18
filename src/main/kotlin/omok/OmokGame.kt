package omok

class OmokGame {
    val grid: OmokGrid = OmokGrid()

    fun checkOmok(
        row: Int,
        col: Int,
    ): Boolean {
        val directions: List<Direction> =
            listOf(
                Direction(0, 1),
                Direction(1, 0),
                Direction(1, 1),
                Direction(1, -1),
            )

        return directions.any { dir ->
            val count = search(dir, row, col) + search(-dir, row, col)
            count >= OMOK_STANDARD
        }
    }

    private fun search(
        direction: Direction,
        row: Int,
        col: Int,
    ): Int {
        val state = grid.board[row][col].state
        var count = DEFAULT_COUNT

        while (grid.board[row + direction.rowDelta * count][col + direction.colDelta * count].state == state) {
            count++
        }

        return count
    }

    companion object {
        private const val DEFAULT_COUNT: Int = 1
        private const val OMOK_STANDARD: Int = 5
    }
}
