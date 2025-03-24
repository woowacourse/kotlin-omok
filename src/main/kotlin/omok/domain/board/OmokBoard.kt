package omok.domain.board

import omok.domain.point.OmokPoints
import omok.domain.point.Point
import omok.domain.rule.Direction
import omok.domain.rule.OmokRule
import omok.domain.rule.OmokRulesFacade
import omok.exception.execute

class OmokBoard(
    private val omokPoints: OmokPoints,
) {
    private val facade: OmokRule = OmokRulesFacade(this)

    fun toMatrix(): List<List<BoardStatus>> = omokPoints.toMatrix()

    fun isNotFull() = omokPoints.toList().any { it.status is BoardStatus.Empty }

    fun addStone(point: Point) =
        execute {
            omokPoints.pointValidation(point)
            omokPoints.moveStone(point)
            updateBlockedPlace()
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

    private fun updateBlockedPlace() {
        omokPoints
            .toList()
            .filter { it.status is BoardStatus.Empty }
            .forEach { point -> addBlockStone(point) }
    }

    private fun addBlockStone(point: Point) {
        if (facade.renjuRulesValidation(point)) {
            omokPoints.moveStone(point.copy(status = BoardStatus.Blocked))
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
        private const val OMOK_MATCH_COUNT = 5
    }
}
