package omok.domain.rule

import omok.domain.board.OmokBoard
import omok.domain.point.Point2
import omok.domain.rule.finder.Direction

interface OmokRule {
    fun isProtected(
        point: Point2,
        board: OmokBoard,
    ): Boolean

    fun isOmok(
        current: Point2,
        board: OmokBoard,
    ): Boolean {
        return Direction.getDirectionPair().any { (d1, d2) ->
            val count1 = search(d1, current, board, target = current)
            val count2 = search(d2, current, board, target = current)
            count1 + count2 - 1 == OMOK_MATCH_COUNT
        }
    }

    private fun search(
        direction: Direction,
        point: Point2,
        board: OmokBoard,
        target: Point2,
    ): Int {
        if (point::class == target::class) {
            val next = board.goto(point, direction)
            return search(direction, next, board, target) + 1
        }
        return 0
    }

    companion object {
        protected const val OMOK_MATCH_COUNT = 5
    }
}
