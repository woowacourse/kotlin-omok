package domain.rule

import domain.position.Position
import domain.stone.Stone
import domain.stone.StoneColor
import domain.stone.Stones

class RenjuRule() : OmokRule {
    private val directions = listOf(RIGHT_DIRECTION, TOP_DIRECTION, RIGHT_TOP_DIRECTION, LEFT_BOTTOM_DIRECTION)
    private lateinit var blackStones: Stones
    private lateinit var whiteStones: Stones

    override fun check(blackStones: Stones, whiteStones: Stones, turn: StoneColor, currentWin: Boolean): Boolean {
        if (turn == StoneColor.WHITE) return false

        this.blackStones = blackStones
        this.whiteStones = whiteStones

        if (check33()) {
            if (currentWin) return false
            return true
        }
        if (check44()) {
            if (currentWin) return false
            return true
        }
        if (checkLongOmok()) return true
        return false
    }

    private fun check33(): Boolean {
        var threeCount = 0

        for (moveDirection in directions) {
            val (forwardCount, forwardEmptyCount) = findStraight(moveDirection, FORWARD_WEIGHT, 3)
            val (backCount, backEmptyCount) = findStraight(moveDirection, BACK_WEIGHT, 3)

            // 만약 빈 칸이 2 미만이고, 같은 돌 개수가 무조건 3이면 3-3 가능성 ok
            if (forwardCount + backCount - 1 == 3 && forwardEmptyCount + backEmptyCount <= 1) {
                // 백돌 양쪽 합 6칸 이내에 2개 이상 있는지 확인한다.
                // 닫혀 있으면 다른 방향 확인
                if (!isBlockedByWhiteStoneInSix(moveDirection)) threeCount++
                if (threeCount == 2) return true
            }
        }
        return false
    }

    private fun check44(): Boolean {
        var fourCount = 0
        for (moveDirection in directions) {
            val (forwardCount, forwardEmptyCount) = findStraight(moveDirection, FORWARD_WEIGHT, 4)
            val (backCount, backEmptyCount) = findStraight(moveDirection, BACK_WEIGHT, 4)
            // 만약 빈 칸이 2 미만이고, 같은 돌 개수가 무조건 4이면 4-4 가능성 ok
            val stoneCount = forwardCount + backCount - 1
            // 1자 4-4
            if (stoneCount >= 5 && forwardEmptyCount == 1 && backEmptyCount == 1) return true
            // 각각 다른 방향 4-4
            if (stoneCount == 4 && forwardEmptyCount + backEmptyCount <= 1) {
                fourCount++
                if (fourCount == 2) return true
            }
        }
        return false
    }

    private fun checkLongOmok(): Boolean {
        for (moveDirection in directions) {
            val forwardCount = findLongOmok(moveDirection, FORWARD_WEIGHT)
            val backCount = findLongOmok(moveDirection, BACK_WEIGHT)

            if (forwardCount + backCount - 1 > 5) return true
        }
        return false
    }

    private fun isBlockedByWhiteStoneInSix(direction: Pair<Int, Int>): Boolean {
        val (oneDirMoveCount, oneDirFound) = checkWhite(
            direction,
            FORWARD_WEIGHT,
        )
        val (otherDirMoveCount, otherDirFound) = checkWhite(
            direction,
            BACK_WEIGHT,
        )

        // 양 방향 6칸 이하에 각각 1개씩 있으면 참
        return oneDirMoveCount + otherDirMoveCount <= 6 && oneDirFound && otherDirFound
    }

    private fun checkWhite(direction: Pair<Int, Int>, weight: Int): Pair<Int, Boolean> {
        val startPosition = blackStones.lastStone.position
        var (curRow, curCol) = Pair(startPosition.row + direction.first * weight, startPosition.col + direction.second * weight)
        var moveCount = 0
        while (inRange(curRow, curCol) && moveCount <= 6) {
            moveCount++
            if (hasStone(whiteStones, curRow, curCol)) return Pair(moveCount, true)
            curRow += direction.first * weight
            curCol += direction.second * weight
        }
        return Pair(moveCount, false)
    }

    private fun findStraight(direction: Pair<Int, Int>, weight: Int = FORWARD_WEIGHT, stoneCount: Int): Pair<Int, Int> {
        val startPosition = blackStones.lastStone.position
        val (startRow, startCol) = Pair(startPosition.row, startPosition.col)
        var sameStoneCount = 1
        var emptyCount = 0
        var (currentRow, currentCol) = Pair(startRow + direction.first * weight, startCol + direction.second * weight)

        // 현재 탐색 방향에
        // 흰 돌이 아니고, 범위 안에 있고
        // 같은 돌의 개수가 stoneCount개 이하이고, 공백이 1개 이하일 때까지
        while (inRange(currentRow, currentCol) && emptyCount <= 1 &&
            sameStoneCount < stoneCount && !hasStone(whiteStones, currentRow, currentCol)
        ) {
            val hasBlackStone = hasStone(blackStones, currentRow, currentCol)
            val hasWhiteStone = hasStone(whiteStones, currentRow, currentCol)
            val isEmpty = !hasBlackStone && !hasWhiteStone
            // 검은 돌이 있는지 확인한다.
            if (hasBlackStone) ++sameStoneCount
            // 빈 칸인지 확인한다.
            if (isEmpty) ++emptyCount
            currentRow += direction.first * weight
            currentCol += direction.second * weight
        }
        currentRow -= direction.first * weight
        currentCol -= direction.second * weight
        // 필요없는 빈칸 개수 빼기
        while ((startRow != currentRow || startCol != currentCol) && !hasStone(blackStones, currentRow, currentCol)) {
            emptyCount -= 1
            currentRow -= direction.first * weight
            currentCol -= direction.second * weight
        }

        return Pair(sameStoneCount, emptyCount)
    }

    private fun findLongOmok(direction: Pair<Int, Int>, weight: Int = FORWARD_WEIGHT): Int {
        val startPosition = blackStones.lastStone.position
        val (startRow, startCol) = Pair(startPosition.row, startPosition.col)
        var sameStoneCount = 1
        var (currentRow, currentCol) = Pair(startRow + direction.first * weight, startCol + direction.second * weight)

        while (inRange(currentRow, currentCol) && hasStone(blackStones, currentRow, currentCol)) {
            // 검은 돌이 있는지 확인한다.
            sameStoneCount++
            currentRow += direction.first * weight
            currentCol += direction.second * weight
        }

        return sameStoneCount
    }

    private fun inRange(x: Int, y: Int) = x in Position.POSITION_RANGE && y in Position.POSITION_RANGE

    private fun hasStone(stones: Stones, row: Int, col: Int) = stones.hasStone(Stone.of(row, col))

    companion object {

        private val RIGHT_DIRECTION = Pair(1, 0)
        private val TOP_DIRECTION = Pair(0, 1)
        private val RIGHT_TOP_DIRECTION = Pair(1, 1)
        private val LEFT_BOTTOM_DIRECTION = Pair(-1, 1)

        private const val FORWARD_WEIGHT = 1
        private const val BACK_WEIGHT = -1
    }
}
