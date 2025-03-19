package omok.domain.board

import omok.domain.point.OmokPoints
import omok.domain.point.Point
import omok.domain.rule.Direction

class OmokBoard(
    private val omokPoints: OmokPoints,
) {
    private var latestStone: Point = Point(OmokColumn.WALL, OmokRow.WALL, StoneStatus.EMPTY)

    val board get() = omokPoints.toList()

    fun isNotFull() = omokPoints.isNotFull()

    fun getLatestStone(): String {
        val dx = OmokColumn.find(latestStone.x.value).name
        val dy = latestStone.y.value
        return dx + dy
    }

    fun addStone(point: Point) {
        omokPoints.addStone(point)
        latestStone = point
    }

    fun getPointAt(
        row: OmokRow,
        column: OmokColumn,
    ): Point {
        return omokPoints.getPointAt(row, column)
    }

    fun goto(
        currentPosition: Point,
        direction: Direction,
    ): Point {
        val newX = currentPosition.x.value + direction.x
        val newY = currentPosition.y.value + direction.y
        return getPointAt(OmokRow.find(newY), OmokColumn.find(newX))
    }

    fun toMatrix(): List<List<StoneStatus>> {
        return omokPoints.toMatrix()
    }
}
