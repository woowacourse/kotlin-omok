package rule

import Stone
import rule.type.Foul
import rule.type.Violation
import rule.wrapper.direction.Direction
import rule.wrapper.position.Position

// typealias Row = Int
// typealias Col = Int
// typealias MoveWeight = Int

// typealias Direction<R, C> = Pair<R, C>

abstract class OmokRule(
    // TODO : Col 객체로 변경 필요
    protected val boardWidth: Int,
    // TODO : Row 객체로 변경 필요
    protected val boardHeight: Int,
) {
    /**
     * The function will determine if the win condition is satisfied.
     * If it is a black stone, it will determine whether there is a foul.
     * On the other hand, whiteStone only checks that the victory condition is satisfied regardless of whether there is a foul play or not.
     *
     * @param blackPositions List of pairs for row and column of black stones.
     * @param whitePositions List of pairs for row and column of white stones.
     * @param startPosition The row and column of the stone that is being placed.
     *
     * @return Returns true if no fouls are played and the win conditions are met.
     * */
    fun checkWin(
        blackPositions: List<Stone>,
        whitePositions: List<Stone>,
        startPosition: Position,
    ): Boolean {
        val satisfyWin = checkSerialSameStonesBiDirection(blackPositions, startPosition, WIN_STANDARD)
        val koState = checkAnyFoulCondition(blackPositions, whitePositions, startPosition)

        if (satisfyWin && koState != Violation.OVERLINE) return true
        return false
    }

    /**
     * When a stone is placed at a specific location, it checks if the same number of stones are in a row.
     * It can also be used to determine if you have won.
     *
     * @param stonesPositions List of stone Positions for the given row and column to check for continuous.
     * @param startPosition The row and column of the stone that is being placed.
     *
     * @return Return if there are as many stones in a row as you are looking for.
     * */
    fun checkSerialSameStonesBiDirection(
        stonesPositions: List<Stone>,
        startPosition: Position,
        sameStoneToCheck: Int,
    ): Boolean {
        val dirIterator = Direction.iterator()

        while (dirIterator.hasNext()) {
            val forwardCount = countSerialStonesOneDirection(stonesPositions, startPosition, dirIterator.next())
            val backCount = countSerialStonesOneDirection(stonesPositions, startPosition, dirIterator.next())
            val totalMoveCount = forwardCount + backCount - 1
            if (totalMoveCount >= sameStoneToCheck) return true
        }
        return false
    }

    /**
     * Returns the number of identical stones in a row in one direction.
     *
     * @param stonesPositions List of stone Positions for the given row and column to check for continuous.
     * @param startPosition The row and column of the stone that is being placed.
     * @param direction Direction you want to explore.
     *
     * @return The number of identical stones placed in a given direction.
     * */
    private fun countSerialStonesOneDirection(
        stonesPositions: List<Stone>,
        startPosition: Position,
        direction: Direction,
    ): Int {
        var sameStoneCount = DEFAULT_SAME_STONE_COUNT
        val rowStep = direction.rowStep
        val colStep = direction.colStep
        var curPosition = startPosition.move(rowStep, colStep)

        while (curPosition.inRange(boardWidth, boardHeight) && stonesPositions isPlaced curPosition) {
            sameStoneCount++
            curPosition = curPosition.move(rowStep, colStep)
        }
        return sameStoneCount
    }

    /**
     * The function will return True if any of the three forbidden moves '3-3', '4-4', and 'overline' is detected.
     *
     * @param blackPositions List of pairs for row and column of black stones.
     * @param whitePositions List of pairs for row and column of white stones.
     * @param startPosition The row and column of the stone that is being placed.
     *
     * @return The result of checking all numbers.
     * */
    fun checkAnyFoulCondition(
        blackPositions: List<Stone>,
        whitePositions: List<Stone>,
        startPosition: Position,
    ): Violation =
        listOf(
            checkDoubleFoul(blackPositions, whitePositions, startPosition, Foul.DOUBLE_THREE),
            checkDoubleFoul(blackPositions, whitePositions, startPosition, Foul.DOUBLE_FOUR),
            checkOverline(blackPositions, startPosition),
        ).lastOrNull { it.state } ?: Violation.NONE

    /**
     * check 'three-three' Position or 'four-four' Position according to the given 'foul type'
     *
     * @param blackPositions List of pairs for row and column of black stones.
     * @param whitePositions List of pairs for row and column of white stones.
     * @param startPosition The row and column of the stone that is being placed.
     *
     * @return Whether the given row and column correspond to 3-3 or 4-4 according to the given 'foul type'.
     * */
    abstract fun checkDoubleFoul(
        blackPositions: List<Stone>,
        whitePositions: List<Stone>,
        startPosition: Position,
        foul: Foul,
    ): Violation

    /**
     * Check 'overline' pattern.
     *
     * @param stonesPositions List of stone Positions for the given row and column to check for overline.
     * @param startPosition The row and column of the stone that is being placed.
     *
     * @return Boolean value indicating whether it is overline.
     * */
    abstract fun checkOverline(
        stonesPositions: List<Stone>,
        startPosition: Position,
    ): Violation

    internal fun switch(
        boardWidth: Int,
        boardHeight: Int,
    ): OmokRule {
        if (this is BlackRenjuRule) return WhiteRenjuRule(boardWidth, boardHeight)
        return BlackRenjuRule(boardWidth, boardHeight)
    }

    // TODO : 라이브러리에서는 원시값이여서 contains로 비교했으나 제네릭 타입이 객체가 되면서 리스트 순회하면서 동일한 지 체크하도록  변경
    protected infix fun List<Stone>.isPlaced(position: Position): Boolean =
        this.any { existedStone -> existedStone.position.isSame(position) }

    companion object {
        @JvmStatic
        protected val WIN_STANDARD: Int = 5
        private const val DEFAULT_SAME_STONE_COUNT = 1
    }
}
