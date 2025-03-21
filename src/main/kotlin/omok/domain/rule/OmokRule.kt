package omok.domain.rule

import omok.domain.board.OmokBoard
import omok.domain.board.StoneStatus
import omok.domain.point.Point
import omok.domain.rule.renjuRule.Direction

interface OmokRule {
    fun isProtected(
        point: Point,
        board: OmokBoard,
    ): Boolean

    fun isOmok(
        current: Point,
        board: OmokBoard,
    ): Boolean {
        return Direction.getDirectionPair().any { (d1, d2) ->
            val count1 = seek(d1, current, current.stoneStatus, board)
            val count2 = seek(d2, current, current.stoneStatus, board)
            count1 + count2 - 1 == OMOK_MATCH_COUNT
        }
    }

    private fun seek(
        direction: Direction,
        point: Point,
        target: StoneStatus,
        board: OmokBoard,
    ): Int {
        if (point.stoneStatus == target) {
            val next = board.goto(point, direction)
            return seek(direction, next, target, board) + 1
        }
        return 0
    }

    companion object {
        private const val OMOK_MATCH_COUNT = 5
    }
}
