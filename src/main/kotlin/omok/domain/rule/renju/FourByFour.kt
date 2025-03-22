package omok.domain.rule.renju

import omok.domain.board.BoardStatus
import omok.domain.board.OmokBoard
import omok.domain.point.Point
import omok.domain.rule.Direction
import omok.domain.stone.StoneColor

class FourByFour(board: OmokBoard) : Renju(board) {
    override fun match(p: Point): Boolean {
        return p.status == BoardStatus.Empty &&
            checkDirectionPairs(p, BoardStatus.Moved(StoneColor.BLACK)) > 1
    }

    override fun checkDirectionPairs(
        current: Point,
        target: BoardStatus,
    ): Int {
        return Direction.getDirectionPair().count { (d1, d2) ->
            val forwardCount = seek(d1, current, target).count
            val previousCount = seek(d2, current, target).count
            val totalCount = forwardCount + previousCount - EMPTY_ADJUSTMENT

            totalCount == REQUIRED_FOUR_STONES
        }
    }
}
