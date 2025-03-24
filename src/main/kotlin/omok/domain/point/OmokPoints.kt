package omok.domain.point

import omok.domain.board.OmokBoard

class OmokPoints {
    var points: List<Point> =
        (1..OmokBoard.MAX_ROW_SIZE).flatMap { row ->
            (1..OmokBoard.MAX_COLUMN_SIZE).map { column ->
                Empty(column, row)
            }
        }
        get() = field.toList()
        private set

    fun getPointAt(
        row: Int,
        column: Int,
    ): Point {
        return points.find { it.x == column && it.y == row }
            ?: Protected(-100, -100)
    }

    fun altStone(point: Point) {
        val position = points.indexOfFirst { it.x == point.x && it.y == point.y }
        val newList = points.toMutableList()
        newList[position] = point
        points = newList
    }

    fun toMatrix(): List<List<Point>> {
        val temp: MutableList<MutableList<Point>> =
            MutableList(OmokBoard.MAX_ROW_SIZE) {
                MutableList(OmokBoard.MAX_COLUMN_SIZE) { Empty.dummy() }
            }

        for (status in points) {
            temp[status.y - 1][status.x - 1] = status
        }

        return temp.toList()
    }

    fun isOccupied(point: Point): Boolean {
        return points.first { it.x == point.x && it.y == point.y } !is Empty
    }

    fun isProtected(point: Point): Boolean {
        if (point is White) return false
        return points.first { it.x == point.x && it.y == point.y } is Protected
    }
}
