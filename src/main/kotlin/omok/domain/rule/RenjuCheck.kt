package omok.domain.rule

import omok.domain.board.OmokBoard
import omok.domain.board.OmokColumn
import omok.domain.board.OmokRow
import omok.domain.board.StoneStatus
import omok.domain.point.Point
import omok.domain.rule.Renju.CheckType

class RenjuCheck(private val board: OmokBoard) : Renju {
    override fun is4x4(current: Point): Boolean =
        current.stoneStatus == StoneStatus.EMPTY &&
            checkDirectionPairs(current, StoneStatus.BLACK, CheckType.FOUR_X_FOUR) > 1

    override fun is3x3(current: Point): Boolean =
        current.stoneStatus == StoneStatus.EMPTY &&
            checkDirectionPairs(current, StoneStatus.BLACK, CheckType.THREE_X_THREE) > 1

    override fun is6mok(current: Point): Boolean =
        current.stoneStatus == StoneStatus.EMPTY &&
            checkDirectionPairs(current, StoneStatus.BLACK, CheckType.SIX_MOK) > 0

    private fun seek(
        direction: Direction,
        point: Point,
        target: StoneStatus,
        depth: Int = 0,
    ): Triple<Int, Boolean, Boolean> {
        val next = board.goto(point, direction)
        val previous = board.goto(point, direction.reverse())
        if (point.stoneStatus == target || (point.stoneStatus == StoneStatus.EMPTY && depth <= 3)) {
            return if (point.stoneStatus == target || depth == 0) {
                seek(direction, next, target, depth + 1).let { Triple(it.first + 1, it.second, it.third) }
            } else {
                seek(direction, next, target, depth + 1)
            }
        }

        return if (point.stoneStatus == StoneStatus.EMPTY &&
            point.x != OmokColumn.WALL &&
            point.y != OmokRow.WALL
        ) {
            if (previous.stoneStatus == StoneStatus.EMPTY && next.stoneStatus == StoneStatus.BLACK) {
                Triple(0, true, true)
            } else {
                Triple(0, true, false)
            }
        } else if (point.stoneStatus == StoneStatus.WHITE && previous.stoneStatus == StoneStatus.EMPTY) {
            Triple(0, true, true)
        } else {
            Triple(0, false, false)
        }
    }

    private fun checkDirectionPairs(
        current: Point,
        target: StoneStatus,
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
