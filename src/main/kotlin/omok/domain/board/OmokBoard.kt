package omok.domain.board

class OmokBoard {
    private var _board: List<Point> =
        OmokRow.entries.flatMap { row ->
            OmokColumn.entries.map { column ->
                Point(column, row, StoneStatus.EMPTY)
            }
        }
    val board get() = _board.toList()

    fun addStone(point: Point) {
        val position = _board.indexOf(Point(point.x, point.y, StoneStatus.EMPTY))
        val newList = _board.toMutableList()
        newList[position] = point
        _board = newList
    }

    fun getPointAt(
        row: OmokRow,
        column: OmokColumn,
    ): Point {
        return _board.find { it.x == column && it.y == row } ?: throw IllegalStateException()
    }
}
