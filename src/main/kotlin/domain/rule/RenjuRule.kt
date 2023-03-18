package domain.rule

import domain.position.Position
import domain.stone.Stone
import domain.stone.Stones

class RenjuRule : OmokRule {
    private val directions = listOf(RIGHT_DIRECTION, TOP_DIRECTION, RIGHT_TOP_DIRECTION, LEFT_BOTTOM_DIRECTION)
    override fun check(blackStones: Stones, whiteStones: Stones, startStone: Stone): Boolean {
        return check33(blackStones, whiteStones, startStone) || check44(blackStones, whiteStones, startStone) || checkLongOmok(blackStones, startStone)
    }

    private fun check33(blackStones: Stones, whiteStones: Stones, startStone: Stone): Boolean {
        return check33AllDirections(blackStones, whiteStones, startStone)
        // blackPlayer에서 돌을 놓고 이 규칙을 확인한다면, 이미 blackStones에는 돌이 들어간 상태니까 startStone을 삭제하고 blackStones.getLastStone으로 해결할 수 있을 것 같음!
        // "금지된 수를 놓으면서 동시에 5도 만들어지는 경우에는 흑 승리로 인정된다." -> 놓고나서 이겼는지 체크하고, 이기지도 않았는데 금수인 경우에는 패배로 바꿔야할 것 같음
    }

    private fun check44(blackStones: Stones, whiteStones: Stones, startStone: Stone): Boolean {
        return check44AllDirections(blackStones, whiteStones, startStone)
    }

    private fun checkLongOmok(blackStones: Stones, startStone: Stone): Boolean {
        for (moveDirection in directions) {
            val forwardCount = findLongOmok(blackStones, startStone.position, moveDirection, FORWARD_WEIGHT)
            val backCount = findLongOmok(blackStones, startStone.position, moveDirection, BACK_WEIGHT)

            if (forwardCount + backCount - 1 > 5) return true
        }
        return false
    }

    private fun check33AllDirections(blackStones: Stones, whiteStones: Stones, startStone: Stone): Boolean {
        var threeCount = 0

        for (moveDirection in directions) {
            val (forwardCount, forwardEmptyCount) =
                findStraight(blackStones, whiteStones, startStone.position, moveDirection, FORWARD_WEIGHT, 3)
            val (backCount, backEmptyCount) =
                findStraight(blackStones, whiteStones, startStone.position, moveDirection, BACK_WEIGHT, 3)

            // 만약 빈 칸이 2 미만이고, 같은 돌 개수가 무조건 3이면 3-3 가능성 ok
            if (forwardCount + backCount - 1 == 3 && forwardEmptyCount + backEmptyCount <= 1) {
                // 백돌 양쪽 합 6칸 이내에 2개 이상 있는지 확인한다.
                // 닫혀 있으면 다른 방향 확인
                if (!isBlockedByWhiteStoneInSix(whiteStones, startStone.position, moveDirection)) threeCount++
                if (threeCount == 2) return true
            }
        }
        return false
    }

    private fun check44AllDirections(blackStones: Stones, whiteStones: Stones, startStone: Stone): Boolean {
        var fourCount = 0
        for (moveDirection in directions) {
            val (forwardCount, forwardEmptyCount) =
                findStraight(blackStones, whiteStones, startStone.position, moveDirection, FORWARD_WEIGHT, 4)
            val (backCount, backEmptyCount) =
                findStraight(blackStones, whiteStones, startStone.position, moveDirection, BACK_WEIGHT, 4)
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

    private fun isBlockedByWhiteStoneInSix(
        whiteStones: Stones,
        position: Position,
        direction: Pair<Int, Int>,
    ): Boolean {
        val (oneDirMoveCount, oneDirFound) = checkWhite(
            whiteStones,
            position,
            direction,
            FORWARD_WEIGHT,
        )
        val (otherDirMoveCount, otherDirFound) = checkWhite(
            whiteStones,
            position,
            direction,
            BACK_WEIGHT,
        )

        // 양 방향 6칸 이하에 각각 1개씩 있으면 참
        return oneDirMoveCount + otherDirMoveCount <= 6 && oneDirFound && otherDirFound
    }

    private fun checkWhite(
        whiteStones: Stones,
        position: Position,
        direction: Pair<Int, Int>,
        weight: Int,
    ): Pair<Int, Boolean> {
        var (curRow, curCol) = Pair(position.row + direction.first * weight, position.col + direction.second * weight)
        var moveCount = 0
        while (inRange(curRow, curCol) && moveCount <= 6) {
            moveCount++
            if (isPlaced(whiteStones, curRow, curCol)) return Pair(moveCount, true)
            curRow += direction.first * weight
            curCol += direction.second * weight
        }
        return Pair(moveCount, false)
    }

    private fun findStraight(
        blackStones: Stones,
        whiteStones: Stones,
        startPosition: Position,
        direction: Pair<Int, Int>,
        weight: Int = FORWARD_WEIGHT,
        stoneCount: Int,
    ): Pair<Int, Int> {
        val (startRow, startCol) = Pair(startPosition.row, startPosition.col)
        var sameStoneCount = 1
        var emptyCount = 0
        var (currentRow, currentCol) = Pair(startRow + direction.first * weight, startCol + direction.second * weight)

        // 현재 탐색 방향에
        // 흰 돌이 아니고, 범위 안에 있고
        // 같은 돌의 개수가 stoneCount개 이하이고, 공백이 1개 이하일 때까지
        while (inRange(currentRow, currentCol) && emptyCount <= 1 &&
            sameStoneCount < stoneCount && !isPlaced(whiteStones, currentRow, currentCol)
        ) {
            val hasBlackStone = isPlaced(blackStones, currentRow, currentCol)
            val hasWhiteStone = isPlaced(whiteStones, currentRow, currentCol)
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
        while ((startRow != currentRow || startCol != currentCol) && !isPlaced(blackStones, currentRow, currentCol)) {
            emptyCount -= 1
            currentRow -= direction.first * weight
            currentCol -= direction.second * weight
        }

        return Pair(sameStoneCount, emptyCount)
    }

    private fun findLongOmok(
        blackStones: Stones,
        startPosition: Position,
        direction: Pair<Int, Int>,
        weight: Int = FORWARD_WEIGHT,
    ): Int {
        val (startRow, startCol) = Pair(startPosition.row, startPosition.col)
        var sameStoneCount = 1
        var (currentRow, currentCol) = Pair(startRow + direction.first * weight, startCol + direction.second * weight)

        while (inRange(currentRow, currentCol) && isPlaced(blackStones, currentRow, currentCol)) {
            // 검은 돌이 있는지 확인한다.
            sameStoneCount++
            currentRow += direction.first * weight
            currentCol += direction.second * weight
        }

        return sameStoneCount
    }

    private fun inRange(x: Int, y: Int) = x in Position.POSITION_RANGE && y in Position.POSITION_RANGE

    private fun isPlaced(stones: Stones, row: Int, col: Int) = stones.hasStone(Stone.of(row, col))

    companion object {

        private val RIGHT_DIRECTION = Pair(1, 0)
        private val TOP_DIRECTION = Pair(0, 1)
        private val RIGHT_TOP_DIRECTION = Pair(1, 1)
        private val LEFT_BOTTOM_DIRECTION = Pair(-1, 1)

        private const val FORWARD_WEIGHT = 1
        private const val BACK_WEIGHT = -1
    }
}
