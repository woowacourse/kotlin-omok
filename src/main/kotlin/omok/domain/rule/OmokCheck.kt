package omok.domain.rule

import omok.domain.board.OmokBoard
import omok.domain.board.Point
import omok.domain.board.StoneStatus

class OmokCheck(private val board: OmokBoard) {
    private fun seek(
        direction: Direction,
        point: Point,
        target: StoneStatus,
    ): Int {
        if (point.stoneStatus == target) {
            val next = board.goto(point, direction)
            return seek(direction, next, target) + 1
        }
        return 0
    }

    fun isOmok(current: Point): Boolean {
        return Direction.getDirectionPair().any { (d1, d2) ->
            val count1 = seek(d1, current, current.stoneStatus)
            val count2 = seek(d2, current, current.stoneStatus)
            count1 + count2 - 1 == 5
        }
    }
}
