package omok.domain.board

import omok.domain.point.OmokPoints
import omok.domain.point.Point
import omok.domain.rule.Direction
import omok.domain.rule.OmokCheck
import omok.domain.rule.RenjuCheck

class OmokBoard(
    private val omokPoints: OmokPoints,
) {
    private var latestStone: Point = Point(OmokColumn.WALL, OmokRow.WALL, StoneStatus.EMPTY)

    val board get() = omokPoints.toList()

    fun toMatrix(): List<List<StoneStatus>> = omokPoints.toMatrix()

    fun isNotFull() = omokPoints.isNotFull()

    fun pointValidation(point: Point) = omokPoints.checkPointValid(point)

    fun getPointAt(
        row: OmokRow,
        column: OmokColumn,
    ): Point = omokPoints.getPointAt(row, column)

    fun getLatestStone(): String? {
        if (latestStone.stoneStatus == StoneStatus.EMPTY) return null
        val dx = OmokColumn.find(latestStone.x.value).name
        val dy = latestStone.y.value
        return dx + dy
    }

    fun addStone(point: Point) {
        omokPoints.addStone(point)
        latestStone = point
        updateProtectedPlace()
    }

    fun determineOmok(point: Point): Boolean {
        val checker = OmokCheck(this)
        return checker.isOmok(point)
    }

    private fun updateProtectedPlace() {
        val checker = RenjuCheck(this)
        for (point in board) {
            if (point.stoneStatus == StoneStatus.EMPTY) {
                val is3x3 = checker.is3x3(point)
                val is4x4 = checker.is4x4(point)
                val is6mok = checker.is6mok(point)
                if (is3x3 || is4x4 || is6mok) {
                    omokPoints.addStone(point.copy(stoneStatus = StoneStatus.PROTECTED))
                }
            }
        }
    }

    fun goto(
        currentPosition: Point,
        direction: Direction,
    ): Point {
        val newX = currentPosition.x.value + direction.x
        val newY = currentPosition.y.value + direction.y
        return getPointAt(OmokRow.find(newY), OmokColumn.find(newX))
    }
}
