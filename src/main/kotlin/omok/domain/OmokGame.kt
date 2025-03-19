package omok.domain

import rule.wrapper.point.Point

class OmokGame {
    val grid: OmokGrid = OmokGrid()

    fun checkOmok(point: Point): Boolean {
        val directions: List<Direction> =
            listOf(
                Direction(0, 1),
                Direction(1, 0),
                Direction(1, 1),
                Direction(1, -1),
            )

        return directions.any { dir ->
            val count = search(dir, point) + search(-dir, point) - 1
            count >= OMOK_STANDARD
        }
    }

    private fun search(
        direction: Direction,
        point: Point,
    ): Int {
        val coordinateX = point.row
        val coordinateY = point.col
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
