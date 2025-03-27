package woowacourse.omok.domain.board

import woowacourse.omok.domain.point.OmokPoints
import woowacourse.omok.domain.point.Point
import woowacourse.omok.domain.rule.Direction
import woowacourse.omok.domain.rule.OmokRule
import woowacourse.omok.domain.rule.OmokRulesFacade

class OmokBoard(
    private val omokPoints: OmokPoints,
) {
    private val rules: OmokRule = OmokRulesFacade(this)

    fun isNotFull() = omokPoints.toList().any { it.status is BoardStatus.Empty }

    fun getMovedPoints() = omokPoints.movedPoints

    fun pointValidation(point: Point) = omokPoints.pointValidation(point)

    fun combine(points: List<Point>) = omokPoints.combine(points)

    fun addStone(point: Point) {
        omokPoints.moveStone(point)
        updateBlockedPlace()
    }

    fun goto(
        currentPosition: Point,
        direction: Direction,
    ): Point {
        val newX = currentPosition.x.value + direction.x
        val newY = currentPosition.y.value + direction.y
        return omokPoints.getPointAt(Row(newY), Column(newX))
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
        rules.renjuRulesValidation(point)?.let {
            omokPoints.moveStone(point.copy(status = BoardStatus.Blocked(it)))
        }
    }

    private fun seek(
        direction: Direction,
        point: Point,
        target: BoardStatus,
    ): Int {
        if (point == Point.WALL || point.status != target) {
            return 0
        }
        val next = goto(point, direction)
        return seek(direction, next, target) + 1
    }

    companion object {
        fun create(): OmokBoard {
            val omokPoints = OmokPoints()
            return OmokBoard(omokPoints)
        }

        const val OMOK_BOARD_SIZE = 15
        private const val OMOK_MATCH_COUNT = 5
    }
}
