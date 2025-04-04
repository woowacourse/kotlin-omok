package woowacourse.omok.domain.rule.renju

import woowacourse.omok.domain.board.BoardStatus
import woowacourse.omok.domain.board.OmokBoard
import woowacourse.omok.domain.exception.RendjuException
import woowacourse.omok.domain.point.Point
import woowacourse.omok.domain.rule.Direction
import woowacourse.omok.domain.stone.StoneColor

class FourByFour(board: OmokBoard) : Renju(board) {
    override fun match(p: Point) {
        val boardEmpty = p.status == BoardStatus.Empty
        val hasMoreThanOneFourByFour = checkDirectionPairs(p, BoardStatus.Moved(StoneColor.BLACK)) > 1

        require(boardEmpty && !hasMoreThanOneFourByFour) {
            throw RendjuException.DoubleFourException
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

            totalCount == REQUIRED_FOUR_STONES
        }
    }
}
