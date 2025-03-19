package omok.domain

import omok.domain.board.OmokBoard
import omok.domain.board.OmokColumn
import omok.domain.board.OmokRow
import omok.domain.board.Point
import omok.domain.board.StoneStatus

class RenjuCheck(private val board: OmokBoard) {

    private enum class CheckType {
        FOUR_X_FOUR, THREE_X_THREE, SIX_MOK
    }

    //문제는 양 방향 다 막혀있어야 한다는 것이 문제임..
    //근데 직접 막히는 것은 한 방향이라도 막혀있으면 되는데, 한 칸 띄고 막히는 것은 양 방향 다 막혀야 한다
    //반환 타입을 Pair->Triple로 바꾸고 직접 막혀있는지, 한 칸 띄고 막혀있는지 반환하면 되지 않을까?
    private fun seek(direction: Direction, point: Point, target: StoneStatus, depth: Int = 0): Pair<Int, Boolean> {
        val next = board.goto(point, direction)
        if (point.stoneStatus == target || (point.stoneStatus == StoneStatus.EMPTY && depth <= 3)) {
            return if (point.stoneStatus == target || depth == 0) {
                seek(direction, next, target, depth + 1).let { Pair(it.first + 1, it.second) }
            } else {
                seek(direction, next, target, depth + 1)
            }
        }
        //열려있는지 확인
        return if (point.stoneStatus == StoneStatus.EMPTY
            && point.x != OmokColumn.WALL
            && point.y != OmokRow.WALL
            ) {
            Pair(0, true)
        } else {
            Pair(0, false)
        }
    }

    private fun checkDirectionPairs(current: Point, target: StoneStatus, checkType: CheckType): Int {
        return Direction.getDirectionPair().count { (d1, d2) ->
            val count1 = seek(d1, current, target).first
            val count2 = seek(d2, current, target).first
            val totalCount = count1 + count2 - 1

            when (checkType) {
                CheckType.FOUR_X_FOUR -> totalCount == 4
                CheckType.THREE_X_THREE -> totalCount == 3 && seek(d1, current, target).second && seek(d2, current, target).second
                CheckType.SIX_MOK -> totalCount > 5
            }
        }
    }

    fun is4x4(current: Point): Boolean =
        current.stoneStatus == StoneStatus.EMPTY &&
                checkDirectionPairs(current, StoneStatus.BLACK, CheckType.FOUR_X_FOUR) > 1

    fun is3x3(current: Point): Boolean =
        current.stoneStatus == StoneStatus.EMPTY &&
                checkDirectionPairs(current, StoneStatus.BLACK, CheckType.THREE_X_THREE) > 1

    fun is6mok(current: Point): Boolean =
        current.stoneStatus == StoneStatus.EMPTY &&
                checkDirectionPairs(current, StoneStatus.BLACK, CheckType.SIX_MOK) > 0
}