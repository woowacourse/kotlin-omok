package omok.domain.board

import omok.domain.point.OmokPoints
import omok.domain.point.Point
import omok.domain.rule.OmokRule
import omok.domain.rule.renjuRule.Direction

class OmokBoard(
    private val omokPoints: OmokPoints,
    private val ruleChecker: OmokRule,
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

    fun goto(
        currentPosition: Point,
        direction: Direction,
    ): Point {
        val newX = currentPosition.x.value + direction.x
        val newY = currentPosition.y.value + direction.y
        return omokPoints.getPointAt(OmokRow.find(newY), OmokColumn.find(newX))
    }

    private fun updateProtectedPlace() {
        omokPoints.toList()
            .filter { it.stoneStatus == StoneStatus.EMPTY }
            .forEach { point ->
                if (ruleChecker.isProtected(point, this)) {
                    omokPoints.altStone(point.copy(stoneStatus = StoneStatus.PROTECTED))
                }
            }
    }

    companion object {
        private const val ERROR_OCCUPIED_POSITION = "해당 위치에는 이미 돌이 놓여 있습니다. 다른 위치를 선택하세요."
        private const val ERROR_PROTECTED_POSITION = "해당 위치는 금수 자리입니다. 다른 위치를 선택하세요."
    }
}
