package woowacourse.omok.domain.rule.lib

import woowacourse.omok.domain.position.Position
import woowacourse.omok.domain.rule.lib.type.Violation
import woowacourse.omok.domain.rule.lib.wrapper.direction.Direction
import woowacourse.omok.domain.stone.Stone

open class OmokRule {
    fun checkDuplicatePosition(
        blackStones: List<Stone>,
        whiteStones: List<Stone>,
        curPosition: Position,
    ): Violation {
        if (blackStones.any { stone -> stone.position.isSame(curPosition) } ||
            whiteStones.any { stone -> stone.position.isSame(curPosition) }
        ) {
            return Violation.DUPLICATE_POSITION
        }
        return Violation.NONE
    }

    open fun checkWin(
        targetStone: List<Stone>,
        otherStones: List<Stone>,
        startPosition: Position,
    ): Boolean {
        val satisfyWin = checkSerialSameStonesBiDirection(targetStone, startPosition, WIN_STANDARD)

        return satisfyWin
    }

    fun checkSerialSameStonesBiDirection(
        stones: List<Stone>,
        startPosition: Position,
        sameStoneToCheck: Int,
    ): Boolean {
        val dirIterator = Direction.iterator()
        while (dirIterator.hasNext()) {
            val forwardCount = countSerialStonesOneDirection(stones, startPosition, dirIterator.next())
            val backCount = countSerialStonesOneDirection(stones, startPosition, dirIterator.next())
            val totalMoveCount = forwardCount + backCount - 1
            if (totalMoveCount >= sameStoneToCheck) return true
        }
        return false
    }

    private fun countSerialStonesOneDirection(
        stones: List<Stone>,
        startPosition: Position,
        direction: Direction,
    ): Int {
        var sameStoneCount = DEFAULT_SAME_STONE_COUNT
        val rowStep = direction.rowStep
        val colStep = direction.colStep
        var curPosition = startPosition
        if (!Position.isMovable(curPosition, rowStep, colStep)) return sameStoneCount

        while (Position.isMovable(curPosition, rowStep, colStep)) {
            curPosition = curPosition.move(rowStep, colStep)
            if (curPosition !in stones) break
            ++sameStoneCount
        }

        return sameStoneCount
    }

    operator fun List<Stone>.contains(position: Position): Boolean = this.any { stone -> stone.position.isSame(position) }

    companion object {
        @JvmStatic
        protected val WIN_STANDARD: Int = 5
        private const val DEFAULT_SAME_STONE_COUNT = 1
    }
}
