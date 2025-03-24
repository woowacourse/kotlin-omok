package omok.domain.point

import omok.domain.board.OmokBoard

class OmokPoints {
    private var points: List<Point2> =
        (1..OmokBoard.MAX_ROW_SIZE).flatMap { row ->
            (1..OmokBoard.MAX_COLUMN_SIZE).map { column ->
                Empty(column, row)
            }
        }

    fun toList(): List<Point2> = points.toList()

    fun getPointAt(
        row: Int,
        column: Int,
    ): Point2 {
        return points.find { it.x == column && it.y == row }
            ?: Protected(-100, -100)
    }

    fun altStone(point: Point2) {
        val position = points.indexOfFirst { it.x == point.x && it.y == point.y }
        val newList = points.toMutableList()
        newList[position] = point
        points = newList
    }

    fun toMatrix(): List<List<Point2>> {
        val temp: MutableList<MutableList<Point2>> =
            MutableList(OmokBoard.MAX_ROW_SIZE) {
                MutableList(OmokBoard.MAX_COLUMN_SIZE) { Empty(-100, -100) }
            }

        for (status in points) {
            temp[status.y - 1][status.x - 1] = status
        }

        return temp.toList()
    }

    fun isOccupied(point: Point2): Boolean {
        return points.first { it.x == point.x && it.y == point.y } !is Empty
    }

    fun isProtected(point: Point2): Boolean {
        if (point is White) return false
        return points.first { it.x == point.x && it.y == point.y } is Protected
    }
}
