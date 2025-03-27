package woowacourse.omok.domain.rule.renju

import woowacourse.omok.domain.board.BoardStatus
import woowacourse.omok.domain.board.OmokBoard
import woowacourse.omok.domain.point.Point
import woowacourse.omok.domain.rule.Direction
import woowacourse.omok.domain.stone.StoneColor

class FourByFour(board: OmokBoard) : Renju(board) {
    override fun match(p: Point): Boolean {
        val isBoardEmpty = p.status == BoardStatus.Empty
        val hasMoreThanOneFourByFour = checkDirectionPairs(p, BoardStatus.Moved(StoneColor.BLACK)) > 1
        return isBoardEmpty && hasMoreThanOneFourByFour
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
