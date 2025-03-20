package omok.domain.board

import omok.domain.point.OmokPoints
import omok.domain.point.Point
import omok.domain.rule.Direction
import omok.domain.rule.OmokCheck
import omok.domain.rule.RenjuCheck

class OmokBoard(
    private val omokPoints: OmokPoints,
) {
    var latestStone: Point = Point(OmokColumn.WALL, OmokRow.WALL, StoneStatus.EMPTY)
        private set

    fun toMatrix(): List<List<StoneStatus>> = omokPoints.toMatrix()

    fun isNotFull() = omokPoints.toList().any { it.stoneStatus == StoneStatus.EMPTY }

    fun pointValidation(point: Point) {
        require(!omokPoints.isOccupied(point)) { ERROR_OCCUPIED_POSITION }
        require(!omokPoints.isProtected(point)) { ERROR_PROTECTED_POSITION }
    }

    fun addStone(point: Point) {
        omokPoints.altStone(point)
        latestStone = point
        updateProtectedPlace()
    }

    fun determineOmok(point: Point): Boolean {
        val checker = OmokCheck(this)
        return checker.isOmok(point)
    }

    private fun updateProtectedPlace() {
        val checker = RenjuCheck(this)
        for (point in omokPoints.toList()) {
            if (point.stoneStatus == StoneStatus.EMPTY) {
                val is3x3 = checker.is3x3(point)
                val is4x4 = checker.is4x4(point)
                val is6mok = checker.is6mok(point)
                if (is3x3 || is4x4 || is6mok) {
                    omokPoints.altStone(point.copy(stoneStatus = StoneStatus.PROTECTED))
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
        return omokPoints.getPointAt(OmokRow.find(newY), OmokColumn.find(newX))
    }

    companion object {
        private const val ERROR_OCCUPIED_POSITION = "해당 위치에는 이미 돌이 놓여 있습니다. 다른 위치를 선택하세요."
        private const val ERROR_PROTECTED_POSITION = "해당 위치는 금수 자리입니다. 다른 위치를 선택하세요."
    }
}
