package omok.domain.board

import omok.domain.ext.toCoordination
import omok.domain.point.OmokPoints
import omok.domain.point.Point
import omok.domain.rule.Direction
import omok.domain.rule.RenjuCheck
import omok.domain.stone.LatestStone

class OmokBoard(
    private val omokPoints: OmokPoints,
) {
    var latestStone: LatestStone = LatestStone("")
        private set

    fun toMatrix(): List<List<BoardStatus>> = omokPoints.toMatrix()

    fun isNotFull() = omokPoints.toList().any { it.status is BoardStatus.Empty }

    fun pointValidation(point: Point) {
        require(!omokPoints.isOccupied(point)) { ERROR_OCCUPIED_POSITION }
        require(!omokPoints.isProtected(point)) { ERROR_PROTECTED_POSITION }
    }

    fun addStone(point: Point) {
        omokPoints.moveStone(point)
        saveLatestStone(point)
        updateProtectedPlace()
    }

    private fun saveLatestStone(point: Point) {
        latestStone = LatestStone(point.toCoordination())
    }

    fun goto(
        currentPosition: Point,
        direction: Direction,
    ): Point {
        val newX = currentPosition.x.value + direction.x
        val newY = currentPosition.y.value + direction.y
        return omokPoints.getPointAt(OmokRow.find(newY), OmokColumn.find(newX))
    }

    fun isOmok(point: Point): Boolean {
        return Direction.getDirectionPair().any { (d1, d2) ->
            val count1 = seek(d1, point, point.status)
            val count2 = seek(d2, point, point.status)
            count1 + count2 - 1 == OMOK_MATCH_COUNT
        }
    }

    private fun updateProtectedPlace() {
        val checker = RenjuCheck(this)
        omokPoints.toList()
            .filter { it.status is BoardStatus.Empty }
            .forEach { point ->
                val isBlocked = checker.is3x3(point) || checker.is4x4(point) || checker.is6mok(point)
                if (isBlocked) {
                    omokPoints.moveStone(point.copy(status = BoardStatus.Blocked))
                }
            }
    }

    private fun seek(
        direction: Direction,
        point: Point,
        target: BoardStatus,
    ): Int {
        if (point.status == target) {
            val next = goto(point, direction)
            return seek(direction, next, target) + 1
        }
        return 0
    }

    companion object {
        private const val ERROR_OCCUPIED_POSITION = "해당 위치에는 이미 돌이 놓여 있습니다. 다른 위치를 선택하세요."
        private const val ERROR_PROTECTED_POSITION = "해당 위치는 금수 자리입니다. 다른 위치를 선택하세요."
        private const val OMOK_MATCH_COUNT = 5
    }
}
