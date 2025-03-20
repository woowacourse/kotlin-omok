package omok.domain.point

import omok.domain.board.OmokColumn
import omok.domain.board.OmokRow
import omok.domain.board.StoneStatus

class OmokPoints {
    private var points: List<Point> =
        OmokRow.entriesWithoutWall().flatMap { row ->
            OmokColumn.entriesWithoutWall().map { column ->
                Point(column, row, StoneStatus.EMPTY)
            }
        }

    fun toList(): List<Point> = points.toList()

    fun getPointAt(
        row: OmokRow,
        column: OmokColumn,
    ): Point {
        return points.find { it.x == column && it.y == row }
            ?: Point(OmokColumn.WALL, OmokRow.WALL, StoneStatus.EMPTY)
    }

    fun altStone(point: Point) {
        val position = points.indexOfFirst { it.x == point.x && it.y == point.y }
        val newList = points.toMutableList()
        newList[position] = point
        points = newList
    }

    fun toMatrix(): List<List<StoneStatus>> {
        val temp =
            MutableList(OmokRow.entriesWithoutWall().size) {
                MutableList(OmokColumn.entriesWithoutWall().size) { StoneStatus.EMPTY }
            }

        for (status in points) {
            temp[status.y.value - 1][status.x.value - 1] = status.stoneStatus
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
