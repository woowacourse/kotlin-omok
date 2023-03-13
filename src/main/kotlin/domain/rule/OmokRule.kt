package domain.rule

import domain.point.Point
import domain.rule.type.Foul
import domain.rule.type.Violation
import rule.wrapper.direction.Directions

typealias Row = Int
typealias Col = Int
typealias MoveWeight = Int

typealias Direction<R, C> = Pair<R, C>

abstract class OmokRule(
    protected val boardWidth: Row,
    protected val boardHeight: Col,
) {
    fun checkWin(myPoints: List<Point>, otherPoints: List<Point>, startPoint: Point): Boolean {
        val satisfyWin = checkSerialSameStonesBiDirection(myPoints, startPoint, WIN_STANDARD)
        val koState = checkAnyFoulCondition(myPoints, otherPoints, startPoint)

        if (satisfyWin && koState != Violation.OVERLINE) return true
        return false
    }

    fun checkAnyFoulCondition(
        myPoints: List<Point>,
        otherPoints: List<Point>,
        startPoint: Point,
    ): Violation = listOf(
        checkDoubleFoul(myPoints, otherPoints, startPoint, Foul.DOUBLE_THREE),
        checkDoubleFoul(myPoints, otherPoints, startPoint, Foul.DOUBLE_FOUR),
        checkOverline(myPoints, startPoint),
    ).lastOrNull { it.state } ?: Violation.NONE

    abstract fun checkDoubleFoul(
        myPoints: List<Point>,
        otherPoints: List<Point>,
        startPoint: Point,
        foul: Foul,
    ): Violation

    abstract fun checkOverline(
        stonesPoints: List<Point>,
        startPoint: Point,
    ): Violation

    fun checkSerialSameStonesBiDirection(
        stonesPoints: List<Point>,
        startPoint: Point,
        sameStoneToCheck: Int,
    ): Boolean {
        val dirIterator = Directions().iterator()

        while (dirIterator.hasNext()) {
            val forwardCount = countSerialStonesOneDirection(stonesPoints, startPoint, dirIterator.next())
            val backCount = countSerialStonesOneDirection(stonesPoints, startPoint, dirIterator.next())
            val totalMoveCount = forwardCount + backCount - 1
            if (totalMoveCount >= sameStoneToCheck) return true
        }
        return false
    }

    private fun countSerialStonesOneDirection(
        stonesPoints: List<Point>,
        startPoint: Point,
        direction: Direction<Row, Col>,
    ): Int {
        var sameStoneCount = DEFAULT_SAME_STONE_COUNT
        val rowStep = direction.first
        val colStep = direction.second
        var curPoint = startPoint.move(rowStep, colStep)
        while (curPoint.inRange(boardWidth, boardHeight) && stonesPoints isPlaced curPoint) {
            sameStoneCount++
            curPoint = curPoint.move(rowStep, colStep)
        }
        return sameStoneCount
    }

    protected infix fun List<Point>.isPlaced(point: Point): Boolean = contains(point)

    companion object {
        @JvmStatic
        protected val WIN_STANDARD: Int = 5
        private const val DEFAULT_SAME_STONE_COUNT = 1
    }
}
