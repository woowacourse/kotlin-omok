package omok.domain.point

import omok.domain.board.BoardStatus
import omok.domain.board.OmokColumn
import omok.domain.board.OmokRow
import omok.domain.stone.StoneColor

class OmokPoints {
    private var points: List<Point> =
        OmokRow.entriesWithoutWall().flatMap { row ->
            OmokColumn.entriesWithoutWall().map { column ->
                Point(column, row, BoardStatus.Empty)
            }
        }

    fun pointValidation(point: Point) {
        require(!blocked(point)) { ERROR_PROTECTED_POSITION }
        require(!occupied(point)) { ERROR_OCCUPIED_POSITION }
    }

    fun toList(): List<Point> = points.toList()

    fun getPointAt(
        row: OmokRow,
        column: OmokColumn,
    ): Point {
        return points.find { it.x == column && it.y == row } ?: Point(OmokColumn.WALL, OmokRow.WALL, BoardStatus.Empty)
    }

    fun moveStone(point: Point) {
        val position = points.indexOfFirst { it.x == point.x && it.y == point.y }
        val newList = points.toMutableList()
        newList[position] = point
        points = newList
    }

    fun toMatrix(): List<List<BoardStatus>> {
        return List(OmokRow.entriesWithoutWall().size) {
            MutableList<BoardStatus>(OmokColumn.entriesWithoutWall().size) { BoardStatus.Empty }
        }.apply {
            points.forEach { this[it.y.value - 1][it.x.value - 1] = it.status }
        }
    }

    private fun occupied(point: Point): Boolean {
        val target = points.first { it.x == point.x && it.y == point.y }.status
        if (point.status == BoardStatus.Moved(StoneColor.WHITE) && target == BoardStatus.Blocked) return false
        return target != BoardStatus.Empty
    }

    private fun blocked(point: Point): Boolean {
        if (point.status == BoardStatus.Moved(StoneColor.WHITE)) return false
        return points.first { it.x == point.x && it.y == point.y }.status == BoardStatus.Blocked
    }

    companion object {
        private const val ERROR_OCCUPIED_POSITION = "해당 위치에는 이미 돌이 놓여 있습니다. 다른 위치를 선택하세요."
        private const val ERROR_PROTECTED_POSITION = "해당 위치는 금수 자리입니다. 다른 위치를 선택하세요."
    }
}
