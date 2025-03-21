package omok.domain.rule

import omok.domain.board.OmokBoard
import omok.domain.board.StoneStatus
import omok.domain.point.Point
import omok.domain.rule.finder.Direction

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
            val count1 = search(d1, current, current.stoneStatus, board)
            val count2 = search(d2, current, current.stoneStatus, board)
            count1 + count2 - 1 == OMOK_MATCH_COUNT
        }
    }

    private fun search(
        direction: Direction,
        point: Point,
        target: StoneStatus,
        board: OmokBoard,
    ): Int {
        if (point.stoneStatus == target) {
            val next = board.goto(point, direction)
            return search(direction, next, target, board) + 1
        }
        return 0
    }

    companion object {
        protected const val OMOK_MATCH_COUNT = 5
    }
}
