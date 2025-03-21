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
    ): Triple<Int, Boolean, Boolean> {
        val next = board.goto(point, direction)
        val previous = board.goto(point, direction.reverse())
        if (point.status == target || (point.status == BoardStatus.Empty && depth <= 3)) {
            return if (point.status == target || depth == 0) {
                seek(direction, next, target, depth + 1).let { Triple(it.first + 1, it.second, it.third) }
            } else {
                if (next.x == OmokColumn.WALL || next.y == OmokRow.WALL) return Triple(0, true, false)
                seek(direction, next, target, depth + 1)
            }
        }

        return if (point.status == BoardStatus.Empty &&
            point.x != OmokColumn.WALL &&
            point.y != OmokRow.WALL
        ) {
            if (previous.status == BoardStatus.Empty && next.status == BoardStatus.Moved(StoneColor.BLACK)) {
                Triple(0, true, true)
            } else {
                Triple(0, true, false)
            }
        } else if (point.status == BoardStatus.Moved(StoneColor.WHITE) && previous.status == BoardStatus.Empty) {
            Triple(0, true, true)
        } else {
            Triple(0, false, false)
        }
    }

    private fun checkDirectionPairs(
        current: Point,
        target: BoardStatus,
        checkType: CheckType,
    ): Int {
        return Direction.getDirectionPair().count { (d1, d2) ->
            val count1 = seek(d1, current, target).first
            val count2 = seek(d2, current, target).first
            val isIndirectlyClosed = seek(d1, current, target).third && seek(d2, current, target).third
            val totalCount = count1 + count2 - 1

            when (checkType) {
                CheckType.FOUR_X_FOUR -> totalCount == 4
                CheckType.THREE_X_THREE ->
                    totalCount == 3 && seek(d1, current, target).second &&
                        seek(
                            d2,
                            current,
                            target,
                        ).second && !isIndirectlyClosed

                CheckType.SIX_MOK -> totalCount > 5
            }
        }
    }
}
