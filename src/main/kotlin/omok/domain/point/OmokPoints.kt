package omok.domain.point

import omok.domain.board.OmokBoard

class OmokPoints {
    var points: List<Point> = listOf()
        get() = field.toList()
        private set

    fun getPointAt(
        row: Int,
        column: Int,
    ): Point {
        val isValidRow = row in 1..OmokBoard.MAX_ROW_SIZE
        val isValidColumn = column in 1..OmokBoard.MAX_COLUMN_SIZE
        return points.find { it.x == column && it.y == row }
            ?: if (isValidRow && isValidColumn) Empty(column, row) else Protected.dummy()
    }

    fun add(point: Point) {
        points += point
    }

    fun isOccupied(point: Point): Boolean {
        return getPointAt(point.y, point.x) !is Empty
    }

    fun isProtected(point: Point): Boolean {
        if (point is White) return false
        return getPointAt(point.y, point.x) is Protected
    }
}
