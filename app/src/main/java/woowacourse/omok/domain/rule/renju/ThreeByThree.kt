package woowacourse.omok.domain.rule.renju

import woowacourse.omok.domain.board.BoardStatus
import woowacourse.omok.domain.board.OmokBoard
import woowacourse.omok.domain.exception.RendjuException
import woowacourse.omok.domain.point.Point
import woowacourse.omok.domain.rule.Direction
import woowacourse.omok.domain.stone.StoneColor

class ThreeByThree(board: OmokBoard) : Renju(board) {
    override fun match(p: Point) {
        val boardEmpty = p.status == BoardStatus.Empty
        val hasMoreThanOneThreeByThree =
            checkDirectionPairs(p, BoardStatus.Moved(StoneColor.BLACK)) > 1

        require(boardEmpty && !hasMoreThanOneThreeByThree) {
            throw RendjuException.DoubleThreeException
        }
    }

    override fun checkDirectionPairs(
        current: Point,
        target: BoardStatus,
    ): Int {
        return Direction.getDirectionPair().count { (d1, d2) ->
            val forwardResult = seek(d1, current, target)
            val previousResult = seek(d2, current, target)

            val forwardCount = forwardResult.count
            val previousCount = previousResult.count

            val isIndirectlyClosed =
                forwardResult.isIndirectlyClosed && previousResult.isIndirectlyClosed

            val totalCount = forwardCount + previousCount - EMPTY_ADJUSTMENT
            val hasThreeStones = totalCount == REQUIRED_THREE_STONES
            val isForwardBlocked = seek(d1, current, target).isBlocked
            val isPreviousBlocked = seek(d2, current, target).isBlocked

            hasThreeStones && isForwardBlocked && isPreviousBlocked && !isIndirectlyClosed
        }
    }
}
