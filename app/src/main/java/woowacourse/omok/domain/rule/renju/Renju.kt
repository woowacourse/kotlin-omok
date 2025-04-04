package woowacourse.omok.domain.rule.renju

import woowacourse.omok.domain.board.BoardStatus
import woowacourse.omok.domain.board.Column
import woowacourse.omok.domain.board.OmokBoard
import woowacourse.omok.domain.board.Row
import woowacourse.omok.domain.point.Point
import woowacourse.omok.domain.rule.Direction
import woowacourse.omok.domain.rule.SeekResult
import woowacourse.omok.domain.stone.StoneColor

sealed class Renju(private val board: OmokBoard) {
    abstract fun match(p: Point)

    abstract fun checkDirectionPairs(
        current: Point,
        target: BoardStatus,
    ): Int

    protected fun seek(
        direction: Direction,
        point: Point,
        target: BoardStatus,
        depth: Int = 0,
    ): SeekResult {
        val next = board.goto(point, direction)
        val previous = board.goto(point, direction.reverse())

        if (point.status == target || (point.status == BoardStatus.Empty && depth <= MAX_SEARCH_DEPTH)) {
            return if (point.status == target || depth == 0) {
                seek(direction, next, target, depth + 1).let {
                    SeekResult(it.count + 1, it.isBlocked, it.isIndirectlyClosed)
                }
            } else {
                if (next.x == Column.WALL || next.y == Row.WALL) {
                    return SeekResult(count = 0, isBlocked = true, isIndirectlyClosed = false)
                }
                seek(direction, next, target, depth + 1)
            }
        }

        return if (point.status == BoardStatus.Empty &&
            point.x != Column.WALL &&
            point.y != Row.WALL
        ) {
            if (previous.status == BoardStatus.Empty && next.status == BoardStatus.Moved(StoneColor.BLACK)) {
                SeekResult(count = 0, isBlocked = true, isIndirectlyClosed = true)
            } else {
                SeekResult(count = 0, isBlocked = true, isIndirectlyClosed = false)
            }
        } else if (point.status == BoardStatus.Moved(StoneColor.WHITE) && previous.status == BoardStatus.Empty) {
            SeekResult(count = 0, isBlocked = true, isIndirectlyClosed = true)
        } else {
            SeekResult(count = 0, isBlocked = false, isIndirectlyClosed = false)
        }
    }

    companion object {
        const val REQUIRED_FOUR_STONES = 4
        const val REQUIRED_THREE_STONES = 3
        const val LIMIT_EXCEEDS_FIVE = 5
        const val MAX_SEARCH_DEPTH = 3
        const val EMPTY_ADJUSTMENT = 1
    }
}
