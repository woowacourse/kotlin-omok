package omok.domain.rule

import omok.domain.board.BoardStatus
import omok.domain.board.OmokBoard
import omok.domain.board.OmokColumn
import omok.domain.board.OmokRow
import omok.domain.point.Point
import omok.domain.rule.Renju.CheckType
import omok.domain.stone.StoneColor

class RenjuCheck(private val board: OmokBoard) : Renju {
    override fun is4x4(current: Point): Boolean =
        current.status == BoardStatus.Empty &&
            checkDirectionPairs(current, BoardStatus.Moved(StoneColor.BLACK), CheckType.FOUR_X_FOUR) > 1

    override fun is3x3(current: Point): Boolean =
        current.status == BoardStatus.Empty &&
            checkDirectionPairs(current, BoardStatus.Moved(StoneColor.BLACK), CheckType.THREE_X_THREE) > 1

    override fun is6mok(current: Point): Boolean =
        current.status == BoardStatus.Empty &&
            checkDirectionPairs(current, BoardStatus.Moved(StoneColor.BLACK), CheckType.SIX_MOK) > 0

    private fun seek(
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
                if (next.x == OmokColumn.WALL || next.y == OmokRow.WALL) {
                    return SeekResult(count = 0, isBlocked = true, isIndirectlyClosed = false)
                }
                seek(direction, next, target, depth + 1)
            }
        }

        return if (point.status == BoardStatus.Empty &&
            point.x != OmokColumn.WALL &&
            point.y != OmokRow.WALL
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

    private fun checkDirectionPairs(
        current: Point,
        target: BoardStatus,
        checkType: CheckType,
    ): Int {
        return Direction.getDirectionPair().count { (d1, d2) ->
            val forwardCount = seek(d1, current, target).count
            val previousCount = seek(d2, current, target).count

            val isIndirectlyClosed =
                seek(d1, current, target).isIndirectlyClosed && seek(d2, current, target).isIndirectlyClosed
            val totalCount = forwardCount + previousCount - EMPTY_ADJUSTMENT

            when (checkType) {
                CheckType.FOUR_X_FOUR -> totalCount == REQUIRED_FOUR_STONES
                CheckType.THREE_X_THREE ->
                    totalCount == REQUIRED_THREE_STONES && seek(d1, current, target).isBlocked &&
                        seek(d2, current, target).isBlocked && !isIndirectlyClosed

                CheckType.SIX_MOK -> totalCount > LIMIT_EXCEEDS_FIVE
            }
        }
    }

    companion object {
        private const val REQUIRED_FOUR_STONES = 4
        private const val REQUIRED_THREE_STONES = 3
        private const val LIMIT_EXCEEDS_FIVE = 5
        private const val MAX_SEARCH_DEPTH = 3
        private const val EMPTY_ADJUSTMENT = 1
    }
}
