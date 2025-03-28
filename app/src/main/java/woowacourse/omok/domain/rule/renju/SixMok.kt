package woowacourse.omok.domain.rule.renju

import woowacourse.omok.domain.board.BoardStatus
import woowacourse.omok.domain.board.OmokBoard
import woowacourse.omok.domain.exception.RendjuExceptions
import woowacourse.omok.domain.point.Point
import woowacourse.omok.domain.rule.Direction
import woowacourse.omok.domain.stone.StoneColor

class SixMok(board: OmokBoard) : Renju(board) {
    override fun match(p: Point) {
        val boardEmpty = p.status == BoardStatus.Empty
        val hasSixOrMoreStones = checkDirectionPairs(p, BoardStatus.Moved(StoneColor.BLACK)) > 0

        require(boardEmpty && !hasSixOrMoreStones) {
            throw RendjuExceptions.OverLineExceptions
        }
    }

    override fun checkDirectionPairs(
        current: Point,
        target: BoardStatus,
    ): Int {
        return Direction.getDirectionPair().count { (d1, d2) ->
            val forwardCount = seek(d1, current, target).count
            val previousCount = seek(d2, current, target).count
            val totalCount = forwardCount + previousCount - EMPTY_ADJUSTMENT
            totalCount > LIMIT_EXCEEDS_FIVE
        }
    }
}
