package omok.domain.board

class OmokBoard {
    private var _board: List<Point> =
        OmokRow.entries.flatMap { row ->
            OmokColumn.entries.map { column ->
                Point(column, row, StoneStatus.EMPTY)
            }
        }
    val board get() = _board.toList()

    fun isOccupied(point: Point): Boolean {
        return _board.first { it.x == point.x && it.y == point.y }.stoneStatus == StoneStatus.EMPTY
    }

    fun addStone(point: Point) {
        require(isOccupied(point)) { ERROR_OCCUPIED_POSITION }
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

    companion object {
        const val ERROR_OCCUPIED_POSITION = "해당 위치에는 이미 돌이 놓여 있습니다. 다른 위치를 선택하세요."
    }
}
