package omok.model

class Board(initialGrid: Map<Point, OmokStone>) {
    private val _grid = initialGrid.toMutableMap()
    val grid get() = _grid.toMap()

    fun put(
        point: Point,
        color: StoneColor,
    ) {
        _grid[point] = OmokStone(point, color)
    }

    operator fun get(point: Point): OmokStone? = grid[point]

    fun last(): OmokStone = grid.entries.last().value
}
