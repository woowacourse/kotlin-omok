package omok.domain.board

import omok.domain.point.OmokPoints
import omok.domain.point.Point
import omok.domain.rule.Direction
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

    fun goto(
        currentPosition: Point,
        direction: Direction,
    ): Point {
        val newX = currentPosition.x.value + direction.x
        val newY = currentPosition.y.value + direction.y
        return omokPoints.getPointAt(OmokRow.find(newY), OmokColumn.find(newX))
    }

    fun isOmok(current: Point): Boolean {
        return Direction.getDirectionPair().any { (d1, d2) ->
            val count1 = seek(d1, current, current.stoneStatus)
            val count2 = seek(d2, current, current.stoneStatus)
            count1 + count2 - 1 == 5
        }
    }

    private fun updateProtectedPlace() {
        val checker = RenjuCheck(this)
        omokPoints.toList()
            .filter { it.stoneStatus == StoneStatus.EMPTY }
            .forEach { point ->
                val isProtected = checker.is3x3(point) || checker.is4x4(point) || checker.is6mok(point)
                if (isProtected) {
                    omokPoints.altStone(point.copy(stoneStatus = StoneStatus.PROTECTED))
                }
            }
    }

    private fun seek(
        direction: Direction,
        point: Point,
        target: StoneStatus,
    ): Int {
        if (point.stoneStatus == target) {
            val next = goto(point, direction)
            return seek(direction, next, target) + 1
        }
        return 0
    }

    companion object {
        private const val ERROR_OCCUPIED_POSITION = "해당 위치에는 이미 돌이 놓여 있습니다. 다른 위치를 선택하세요."
        private const val ERROR_PROTECTED_POSITION = "해당 위치는 금수 자리입니다. 다른 위치를 선택하세요."
    }
}
