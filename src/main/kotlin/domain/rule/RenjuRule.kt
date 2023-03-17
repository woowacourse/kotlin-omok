package domain.rule

import domain.position.Position
import domain.stone.Stone
import domain.stone.Stones

class RenjuRule : OmokRule {
    private var threeCount: Int = 0

    override fun check(blackStones: Stones, whiteStones: Stones, startStone: Stone): Boolean {
        return check33(blackStones, whiteStones, startStone) || check44(blackStones, whiteStones, startStone) || checkLongOmok(blackStones, startStone)
    }

    fun check33(blackStones: Stones, whiteStones: Stones, startStone: Stone): Boolean {
        return check33AllDirections(blackStones, whiteStones, startStone)
    }

    fun check44(blackStones: Stones, whiteStones: Stones, startStone: Stone): Boolean {
        return check44AllDirections(blackStones, whiteStones, startStone)
    }

    fun checkLongOmok(blackStones: Stones, startStone: Stone): Boolean {
        val directions = listOf(RIGHT_DIRECTION, TOP_DIRECTION, RIGHT_TOP_DIRECTION, LEFT_BOTTOM_DIRECTION)
        for (moveDirection in directions) {
            val forwardCount = findLongOmok(blackStones, startStone.position, moveDirection, FORWARD_WEIGHT)
            val backCount = findLongOmok(blackStones, startStone.position, moveDirection, BACK_WEIGHT)

            if (forwardCount + backCount - 1 > 5) {
                return true
            }
        }
        return false
    }

    private fun check33AllDirections(blackStones: Stones, whiteStones: Stones, startStone: Stone): Boolean {
        val directions = listOf(RIGHT_DIRECTION, TOP_DIRECTION, RIGHT_TOP_DIRECTION, LEFT_BOTTOM_DIRECTION)

        for (moveDirection in directions) {
            val (forwardCount, forwardEmptyCount) =
                findThree(blackStones, whiteStones, startStone.position, moveDirection, FORWARD_WEIGHT)
            val (backCount, backEmptyCount) =
                findThree(blackStones, whiteStones, startStone.position, moveDirection, BACK_WEIGHT)

            // 만약 빈 칸이 2 미만이고, 같은 돌 개수가 무조건 3이면 3-3 가능성 ok
            // 현재 방향과 3인지 여부를 확인한다.
            if (forwardCount + backCount - 1 == 3 && forwardEmptyCount + backEmptyCount <= 1) {
                val (upDownDir, leftRightDir) = moveDirection

                // 백돌 양쪽 합 6칸 이내에 2개 이상 있는지 확인한다.
                // 닫혀 있으면 다른 방향 확인
                if (!isBlockedByWhiteStoneInSix(whiteStones, startStone.position.row, startStone.position.col, upDownDir, leftRightDir)) {
                    threeCount++
                }
                if (threeCount == 2) {
                    return true
                }
            }
        }
        return false
    }

    private fun check44AllDirections(blackStones: Stones, whiteStones: Stones, startStone: Stone): Boolean {
        val directions = listOf(RIGHT_DIRECTION, TOP_DIRECTION, RIGHT_TOP_DIRECTION, LEFT_BOTTOM_DIRECTION)
        var fourCount = 0
        for (moveDirection in directions) {
            val (forwardCount, forwardEmptyCount) =
                findFour(blackStones, whiteStones, startStone.position, moveDirection, FORWARD_WEIGHT)
            val (backCount, backEmptyCount) =
                findFour(blackStones, whiteStones, startStone.position, moveDirection, BACK_WEIGHT)

            // 만약 빈 칸이 2 미만이고, 같은 돌 개수가 무조건 3이면 3-3 가능성 ok
            // 현재 방향과 3인지 여부를 확인한다.
            if (forwardCount + backCount - 1 == 4 && forwardEmptyCount + backEmptyCount <= 1) {
                fourCount++
                if (fourCount == 2) {
                    return true
                }
            }
        }
        return false
    }

    private fun isBlockedByWhiteStoneInSix(
        whiteStones: Stones,
        row: Int,
        col: Int,
        upDownDir: Int,
        leftRightDir: Int,
    ): Boolean {
        val (oneDirMoveCount, oneDirFound) = checkWhite(
            whiteStones,
            row + upDownDir,
            col + leftRightDir,
            upDownDir,
            leftRightDir,
        )
        val (otherDirMoveCount, otherDirFound) = checkWhite(
            whiteStones,
            row - upDownDir,
            col - leftRightDir,
            upDownDir * -1,
            leftRightDir * -1,
        )

        // 양 방향 6칸 이하에 각각 1개씩 있으면 참
        if (oneDirMoveCount + otherDirMoveCount <= 6 && oneDirFound && otherDirFound) {
            return true
        }
        return false
    }

    private fun checkWhite(
        whiteStones: Stones,
        row: Int,
        col: Int,
        upDownDir: Int,
        leftRightDir: Int,
    ): Pair<Int, Boolean> {
        var (curRow, curCol) = Pair(row, col)
        var moveCount = 0
        while (inRange(curRow, curCol) && moveCount <= 6) {
            moveCount++
            if (whiteStones.hasStone(Stone.of(curRow, curCol))) {
                return Pair(moveCount, true)
            }
            curRow += upDownDir
            curCol += leftRightDir
        }
        return Pair(moveCount, false)
    }

    private fun findThree(
        blackStones: Stones,
        whiteStones: Stones,
        startPosition: Position,
        direction: Pair<Int, Int>,
        weight: Int = FORWARD_WEIGHT,
    ): Pair<Int, Int> {
        val (startRow, startCol) = Pair(startPosition.row, startPosition.col)
        var sameStoneCount = 1
        var emptyCount = 0
        var (currentRow, currentCol) = Pair(startRow + direction.first * weight, startCol + direction.second * weight)

        // 현재 탐색 방향에
        // 흰 돌이 아니고, 범위 안에 있고
        // 같은 돌의 개수가 3개 이하이고, 공백이 1개 이하일 때까지
        while (inRange(currentRow, currentCol) && emptyCount <= 1 &&
            sameStoneCount < 3 && !whiteStones.hasStone(Stone.of(currentRow, currentCol))
        ) {
            // 검은 돌이 있는지 확인한다.
            if (blackStones.hasStone(Stone.of(currentRow, currentCol))) ++sameStoneCount
            // 빈 칸인지 확인한다.
            if (!blackStones.hasStone(Stone.of(currentRow, currentCol)) &&
                !whiteStones.hasStone(Stone.of(currentRow, currentCol))
            ) {
                ++emptyCount
            }
            currentRow += direction.first * weight
            currentCol += direction.second * weight
        }

        if (sameStoneCount == 1) emptyCount = 0 // B X X X
        if (sameStoneCount == 2) emptyCount = 1 // B X B
        if (sameStoneCount == 2 && !blackStones.hasStone(Stone.of(currentRow - direction.first * weight, currentCol - direction.second * weight)) &&
            !whiteStones.hasStone(Stone.of(currentRow - direction.first * weight, currentCol - direction.second * weight))
        ) {
            emptyCount -= 1 // B B X W
        }

        return Pair(sameStoneCount, emptyCount)
    }

    private fun findFour(
        blackStones: Stones,
        whiteStones: Stones,
        startPosition: Position,
        direction: Pair<Int, Int>,
        weight: Int = FORWARD_WEIGHT,
    ): Pair<Int, Int> {
        val (startRow, startCol) = Pair(startPosition.row, startPosition.col)
        var sameStoneCount = 1
        var emptyCount = 0
        var (currentRow, currentCol) = Pair(startRow + direction.first * weight, startCol + direction.second * weight)

        // 현재 탐색 방향에
        // 흰 돌이 아니고, 범위 안에 있고
        // 같은 돌의 개수가 3개 이하이고, 공백이 1개 이하일 때까지
        while (inRange(currentRow, currentCol) && emptyCount <= 1 &&
            sameStoneCount < 4 && !whiteStones.hasStone(Stone.of(currentRow, currentCol))
        ) {
            // 검은 돌이 있는지 확인한다.
            if (blackStones.hasStone(Stone.of(currentRow, currentCol))) ++sameStoneCount
            // 빈 칸인지 확인한다.
            if (!blackStones.hasStone(Stone.of(currentRow, currentCol)) &&
                !whiteStones.hasStone(Stone.of(currentRow, currentCol))
            ) {
                ++emptyCount
            }
            currentRow += direction.first * weight
            currentCol += direction.second * weight
        }

        currentRow -= direction.first * weight
        currentCol -= direction.second * weight

        while (inRange(currentRow, currentCol) && !blackStones.hasStone(Stone.of(currentRow, currentCol))) {
            if (whiteStones.hasStone(Stone.of(currentRow, currentCol))) {
                currentRow -= direction.first * weight
                currentCol -= direction.second * weight
                continue
            }
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
        var emptyCount = 0
        var (currentRow, currentCol) = Pair(startRow + direction.first * weight, startCol + direction.second * weight)

        // 현재 탐색 방향에
        // 흰 돌이 아니고, 범위 안에 있고
        // 같은 돌의 개수가 3개 이하이고, 공백이 1개 이하일 때까지
        while (inRange(currentRow, currentCol) && blackStones.hasStone(Stone.of(currentRow, currentCol))) {
            // 검은 돌이 있는지 확인한다.
            sameStoneCount++
            currentRow += direction.first * weight
            currentCol += direction.second * weight
        }

        return sameStoneCount
    }

    private fun inRange(x: Int, y: Int) = x in Position.POSITION_RANGE && y in Position.POSITION_RANGE

    companion object {
        private val RIGHT_DIRECTION = Pair(1, 0)
        private val TOP_DIRECTION = Pair(0, 1)
        private val RIGHT_TOP_DIRECTION = Pair(1, 1)
        private val LEFT_BOTTOM_DIRECTION = Pair(-1, 1)

        private const val FORWARD_WEIGHT = 1
        private const val BACK_WEIGHT = -1
    }
}
