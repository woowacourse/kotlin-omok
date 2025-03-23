package rule

import domain.position.Position
import domain.stone.Stone
import rule.type.Foul
import rule.type.Violation
import rule.wrapper.direction.Direction

abstract class OmokRule {
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

    fun checkAnyFoulCondition(
        blackStones: List<Stone>,
        whiteStones: List<Stone>,
        startPosition: Position,
    ): Violation =
        listOf(
            checkDoubleFoul(blackStones, whiteStones, startPosition, Foul.DOUBLE_THREE),
            checkDoubleFoul(blackStones, whiteStones, startPosition, Foul.DOUBLE_FOUR),
            checkOverline(blackStones, startPosition),
            checkDuplicatePosition(blackStones, whiteStones, startPosition),
        ).lastOrNull { it.state } ?: Violation.NONE

    private fun checkDuplicatePosition(
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

    abstract fun checkWin(
        blackStones: List<Stone>,
        whiteStones: List<Stone>,
        startPosition: Position,
    ): Boolean

    abstract fun checkDoubleFoul(
        blackStones: List<Stone>,
        whiteStones: List<Stone>,
        startPosition: Position,
        foul: Foul,
    ): Violation

    abstract fun checkOverline(
        stones: List<Stone>,
        startPosition: Position,
    ): Violation

    fun switch(): OmokRule {
        if (this is BlackRenjuRule) return WhiteRenjuRule()
        return BlackRenjuRule()
    }

    operator fun List<Stone>.contains(position: Position): Boolean = this.any { stone -> stone.position.isSame(position) }

    companion object {
        @JvmStatic
        protected val WIN_STANDARD: Int = 5
        private const val DEFAULT_SAME_STONE_COUNT = 1
    }
}
