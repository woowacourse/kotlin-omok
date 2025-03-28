package woowacourse.omok.domain.rule.lib

import woowacourse.omok.domain.position.Position
import woowacourse.omok.domain.rule.lib.type.Foul
import woowacourse.omok.domain.rule.lib.type.Violation
import woowacourse.omok.domain.rule.lib.type.Violation.Companion.FOUL_CONDITION_SIZE
import woowacourse.omok.domain.rule.lib.type.Violation.Companion.MAX_EMPTY_SIZE
import woowacourse.omok.domain.rule.lib.type.Violation.Companion.OVERLINE_SIZE
import woowacourse.omok.domain.rule.lib.type.WhiteBlocked
import woowacourse.omok.domain.rule.lib.wrapper.direction.Direction
import woowacourse.omok.domain.stone.Stone

class RenjuRule : OmokRule() {
    override fun checkWin(
        targetStone: List<Stone>,
        otherStones: List<Stone>,
        startPosition: Position,
    ): Boolean {
        val satisfyWin = super.checkSerialSameStonesBiDirection(targetStone, startPosition, WIN_STANDARD)
        val koState = checkAnyFoulCondition(targetStone, otherStones, startPosition)

        return satisfyWin && koState != Violation.OVERLINE
    }

    fun checkAnyFoulCondition(
        blackStones: List<Stone>,
        whiteStones: List<Stone>,
        startPosition: Position,
    ): Violation =
        listOf(
            checkDoubleFoul(blackStones, whiteStones, startPosition, Foul.DOUBLE_THREE),
            checkDoubleFoul(blackStones, whiteStones, startPosition, Foul.DOUBLE_FOUR),
            checkOverline(blackStones, startPosition),
            super.checkDuplicatePosition(blackStones, whiteStones, startPosition),
        ).lastOrNull { it.state } ?: Violation.NONE

    private fun checkDoubleFoul(
        blackStones: List<Stone>,
        whiteStones: List<Stone>,
        startPosition: Position,
        foul: Foul,
    ): Violation = checkFoulByAllDirections(blackStones, whiteStones, startPosition, foul)

    private fun checkOverline(
        stones: List<Stone>,
        startPosition: Position,
    ): Violation {
        if (checkSerialSameStonesBiDirection(stones, startPosition, OVERLINE_SIZE)) return Violation.OVERLINE
        return Violation.NONE
    }

    private fun checkFoulByAllDirections(
        blackStones: List<Stone>,
        whiteStones: List<Stone>,
        startPosition: Position,
        foul: Foul,
    ): Violation {
        var continuousStones = 0
        val dirIterator = Direction.iterator()

        while (dirIterator.hasNext()) {
            val forwardDir = dirIterator.next()
            val backDir = dirIterator.next()

            val (forwardCount, forwardEmptyCount) =
                findStraight(
                    blackStones,
                    whiteStones,
                    startPosition,
                    forwardDir,
                    foul,
                )
            val (backCount, backEmptyCount) = findStraight(blackStones, whiteStones, startPosition, backDir, foul)
            val totalStoneCount = forwardCount + backCount - 1
            val totalEmptyCount = forwardEmptyCount + backEmptyCount

            when (foul) {
                Foul.DOUBLE_THREE -> {
                    if (totalStoneCount == foul.size && totalEmptyCount <= MAX_EMPTY_SIZE) {
                        val blockedStatus = isBlockedByWhiteStoneInSix(whiteStones, startPosition, forwardDir)
                        if (blockedStatus == WhiteBlocked.NON_BLOCK) continuousStones++
                        if (continuousStones == FOUL_CONDITION_SIZE) return Violation.DOUBLE_THREE
                    }
                }

                Foul.DOUBLE_FOUR -> {
                    if (totalStoneCount > foul.size && forwardEmptyCount == 1 && backEmptyCount == 1) return Violation.DOUBLE_FOUR
                    if (totalStoneCount == foul.size && totalEmptyCount <= MAX_EMPTY_SIZE) continuousStones++
                    if (continuousStones == FOUL_CONDITION_SIZE) return Violation.DOUBLE_FOUR
                }
            }
        }
        return Violation.NONE
    }

    private fun findStraight(
        blackStones: List<Stone>,
        whiteStones: List<Stone>,
        startPosition: Position,
        direction: Direction,
        foul: Foul,
    ): Pair<Int, Int> {
        var sameStoneCount = DEFAULT_SAME_STONE_COUNT
        var emptyCount = DEFAULT_EMPTY_COUNT
        val rowStep = direction.rowStep
        val colStep = direction.colStep
        var curPosition = startPosition

        while (Position.isMovable(curPosition, rowStep, colStep)) {
            if (isInvalidPosition(whiteStones, curPosition, emptyCount, sameStoneCount, foul)) break
            curPosition = curPosition.move(rowStep, colStep)
            val hasBlackStone = curPosition in blackStones
            val hasWhiteStone = curPosition in whiteStones
            val isEmpty = !hasBlackStone && !hasWhiteStone
            if (hasBlackStone) ++sameStoneCount
            if (isEmpty) ++emptyCount
        }

        while (!curPosition.isSame(startPosition) && curPosition !in blackStones) {
            emptyCount--
            curPosition = curPosition.move(-rowStep, -colStep)
        }
        return Pair(sameStoneCount, emptyCount)
    }

    private fun isInvalidPosition(
        whiteStones: List<Stone>,
        curPosition: Position,
        emptyCount: Int,
        sameStoneCount: Int,
        foul: Foul,
    ): Boolean = curPosition in whiteStones || emptyCount > MAX_EMPTY_SIZE || sameStoneCount >= foul.size

    private fun isBlockedByWhiteStoneInSix(
        whiteStones: List<Stone>,
        position: Position,
        direction: Direction,
    ): WhiteBlocked {
        val (oneDirMoveCount, oneDirFound) = checkWhite(whiteStones, position, direction, FORWARD_WEIGHT)
        val (otherDirMoveCount, otherDirFound) = checkWhite(whiteStones, position, direction, BACK_WEIGHT)
        val totalMoveCount = oneDirMoveCount + otherDirMoveCount
        return WhiteBlocked.from(
            totalMoveCount <= WhiteBlocked.INNER_DISTANCE && oneDirFound && otherDirFound,
        )
    }

    private fun checkWhite(
        whiteStones: List<Stone>,
        position: Position,
        direction: Direction,
        weight: Int,
    ): Pair<Int, Boolean> {
        val rowStep = direction.rowStep * weight
        val colStep = direction.colStep * weight
        var moveCount = 0
        var curPosition = position
        while (Position.isMovable(curPosition, rowStep, colStep) && moveCount <= WhiteBlocked.INNER_DISTANCE) {
            curPosition = curPosition.move(rowStep, colStep)
            moveCount++
            if (curPosition in whiteStones) return Pair(moveCount, true)
        }
        return Pair(moveCount, false)
    }

    companion object {
        private const val FORWARD_WEIGHT = 1
        private const val BACK_WEIGHT = -1

        private const val DEFAULT_SAME_STONE_COUNT = 1
        private const val DEFAULT_EMPTY_COUNT = 0
    }
}
