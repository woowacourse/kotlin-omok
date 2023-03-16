package domain.rule

import domain.position.Position
import domain.stone.Stone
import domain.stone.Stones

class RenjuRule : OmokRule {
    private var threeCount: Int = 0

    override fun check(blackStones: Stones, whiteStones: Stones, startStone: Stone): Boolean {
        return check33(blackStones, whiteStones, startStone)
    }

    private fun check33(blackStones: Stones, whiteStones: Stones, startStone: Stone): Boolean {
        return checkAllDirections(blackStones, whiteStones, startStone)
    }

    private fun checkAllDirections(blackStones: Stones, whiteStones: Stones, startStone: Stone): Boolean {
        val directions = listOf(RIGHT_DIRECTION, TOP_DIRECTION, RIGHT_TOP_DIRECTION, LEFT_BOTTOM_DIRECTION)

        for (moveDirection in directions) {
            val (forwardCount, forwardEmptyCount) =
                findThree(blackStones, whiteStones, startStone.position, moveDirection, FORWARD_WEIGHT)
            val (backCount, backEmptyCount) =
                findThree(blackStones, whiteStones, startStone.position, moveDirection, BACK_WEIGHT)

            // 만약 빈 칸이 2 미만이고, 같은 돌 개수가 무조건 3이면 3-3 가능성 ok
            // 현재 방향과 3인지 여부를 확인한다.
            println(moveDirection)
            println("forwardCount : $forwardCount    backCount : $backCount")
            println("forwardEmptyCount : $forwardEmptyCount     backEmptyCount : $backEmptyCount")
            if (forwardCount + backCount - 1 == 3 && forwardEmptyCount + backEmptyCount <= 1) {
                val (upDownDir, leftRightDir) = moveDirection

                // 백돌 양쪽 합 6칸 이내에 2개 이상 있는지 확인한다.
                // 닫혀 있으면 다른 방향 확인
                println("ㅇㅇㅇ")
                if (!isBlockedByWhiteStoneInSix(whiteStones, startStone.position.row, startStone.position.col, upDownDir, leftRightDir)) {
                    threeCount++
                    println("ㅇㅇㅇ2")
                }
                if (threeCount == 2) {
                    // 금수
                    println("금수!!")
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
        leftRightDir: Int
    ): Boolean {
        val (oneDirMoveCount, oneDirFound) = checkWhite(
            whiteStones,
            row + upDownDir,
            col + leftRightDir,
            upDownDir,
            leftRightDir
        )
        val (otherDirMoveCount, otherDirFound) = checkWhite(
            whiteStones,
            row - upDownDir,
            col - leftRightDir,
            upDownDir,
            leftRightDir
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
        leftRightDir: Int
    ): Pair<Int, Boolean> {
        var (curRow, curCol) = Pair(row, col)
        var moveCount = 1
        while (!whiteStones.hasStone(Stone.of(curRow, curCol)) && moveCount <= 6) {
            curRow += upDownDir
            curCol += leftRightDir
            moveCount++
            if (whiteStones.hasStone(Stone.of(curRow, curCol))) {
                return Pair(moveCount, true)
            }
        }
        return Pair(moveCount, false)
    }

    private fun findThree(
        blackStones: Stones,
        whiteStones: Stones,
        startPosition: Position,
        direction: Pair<Int, Int>,
        weight: Int = FORWARD_WEIGHT
    ): Pair<Int, Int> {
        val (startRow, startCol) = Pair(startPosition.row, startPosition.col)
        var sameStoneCount = 1
        var emptyCount = 0
        var (currentRow, currentCol) = Pair(startRow + direction.first * weight, startCol + direction.second * weight)

        // 현재 탐색 방향에
        // 흰 돌이 아니고, 범위 안에 있고
        // 같은 돌의 개수가 3개 이하이고, 공백이 1개 이하일 때까지
        while (inRange(currentRow, currentCol) && emptyCount <= 1 &&
            sameStoneCount <= 3 && !whiteStones.hasStone(Stone.of(currentRow, currentCol))
        ) {
            // 검은 돌이 있는지 확인한다.
            if (blackStones.hasStone(Stone.of(currentRow, currentCol))) {
                ++sameStoneCount
            }
            // 빈 칸인지 확인한다.
            if (!blackStones.hasStone(Stone.of(currentRow, currentCol)) &&
                !whiteStones.hasStone(Stone.of(currentRow, currentCol))
            ) {
                if (sameStoneCount == 3) break
                ++emptyCount
            }
            currentRow += direction.first * weight
            currentCol += direction.second * weight
        }

        if (sameStoneCount == 1) emptyCount = 0

        // println("현재 방향 : ${direction.first * weight} ${direction.second * weight}")
        // println("검은 돌 : $sameStoneCount / 빈 칸 : $emptyCount")

        return Pair(sameStoneCount, emptyCount)
    }

    private fun inRange(x: Int, y: Int) = x in Position.POSITION_RANGE && y in Position.POSITION_RANGE

    companion object {

        private val RIGHT_DIRECTION = Pair(1, 0)
        private val TOP_DIRECTION = Pair(0, 1)
        private val RIGHT_TOP_DIRECTION = Pair(1, 1)
        private val LEFT_BOTTOM_DIRECTION = Pair(-1, -1)

        private const val FORWARD_WEIGHT = 1
        private const val BACK_WEIGHT = -1
    }
}
