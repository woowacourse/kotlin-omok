package omok.domain

class OmokGame {
    val grid: OmokGrid = OmokGrid()

    fun checkOmok(position: Position): Boolean {
        val directions: List<Direction> =
            listOf(
                Direction(0, 1),
                Direction(1, 0),
                Direction(1, 1),
                Direction(1, -1),
            )

        return directions.any { dir ->
            val count = search(dir, position) + search(-dir, position) - 1
            count >= OMOK_STANDARD
        }
    }

    private fun search(
        direction: Direction,
        position: Position,
    ): Int {
        val coordinateX = position.row
        val coordinateY = position.col
        val state = grid.board[coordinateX][coordinateY]
        var count = DEFAULT_COUNT

        while (checkRange(coordinateX + direction.rowDelta * count, coordinateY + direction.colDelta * count) &&
            grid.board[coordinateX + direction.rowDelta * count][coordinateY + direction.colDelta * count] == state
        ) {
            count++
        }

        return count
    }

    private fun checkRange(
        coordinateX: Int,
        coordinateY: Int,
    ): Boolean {
        return coordinateX in (MIN_BOUND..MAX_BOUND) && coordinateY in (MIN_BOUND..MAX_BOUND)
    }

    companion object {
        private const val DEFAULT_COUNT: Int = 0
        private const val OMOK_STANDARD: Int = 5
        const val MIN_BOUND = 0
        const val MAX_BOUND = 14
    }
}
