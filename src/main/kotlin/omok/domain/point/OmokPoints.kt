package omok.domain.point

import omok.domain.board.OmokBoard
import omok.domain.board.StoneStatus

class OmokPoints {
    private var points: List<Point> =
        (1..OmokBoard.MAX_ROW_SIZE).flatMap { row ->
            (1..OmokBoard.MAX_COLUMN_SIZE).map { column ->
                Point(column, row, StoneStatus.EMPTY)
            }
        }

    fun toList(): List<Point> = points.toList()

    fun getPointAt(
        row: Int,
        column: Int,
    ): Point {
        return points.find { it.x == column && it.y == row }
            ?: Point(-100, -100, StoneStatus.PROTECTED)
    }

    fun altStone(point: Point) {
        val position = points.indexOfFirst { it.x == point.x && it.y == point.y }
        val newList = points.toMutableList()
        newList[position] = point
        points = newList
    }

    fun toMatrix(): List<List<StoneStatus>> {
        val temp =
            MutableList(OmokBoard.MAX_ROW_SIZE) {
                MutableList(OmokBoard.MAX_COLUMN_SIZE) { StoneStatus.EMPTY }
            }

        for (status in points) {
            temp[status.y - 1][status.x - 1] = status.stoneStatus
        }

        return temp.toList()
    }

    fun isOccupied(point: Point): Boolean {
        return points.first { it.x == point.x && it.y == point.y }.stoneStatus != StoneStatus.EMPTY
    }

    fun isProtected(point: Point): Boolean {
        if (point.stoneStatus == StoneStatus.WHITE) return false
        return points.first { it.x == point.x && it.y == point.y }.stoneStatus == StoneStatus.PROTECTED
    }
}
