package omok.domain.rule

import omok.domain.OmokGame
import omok.domain.Point
import rule.facade.BlackRenjuRule

class BlackStoneRule(
    boardSize: Int = OmokGame.DEFAULT_BOARD_SIZE,
) : OmokRule(boardSize) {
    private val renjuRule = BlackRenjuRule(boardSize, boardSize)

    override fun checkViolation(
        thisPoints: Set<Point>,
        otherPoints: Set<Point>,
        startPoint: Point,
    ): Violation =
        when {
            isDoubleFourFoul(thisPoints, otherPoints, startPoint) -> Violation.DOUBLE_FOUR
            isDoubleThreeFoul(thisPoints, otherPoints, startPoint) -> Violation.DOUBLE_THREE
            isOverlineFoul(thisPoints, startPoint) -> Violation.OVERLINE
            else -> Violation.NONE
        }

    private fun isDoubleThreeFoul(
        blackPoints: Set<Point>,
        whitePoints: Set<Point>,
        startPoint: Point,
    ): Boolean =
        renjuRule.checkDoubleThreeFoul(
            blackPoints.map { it.toPair() },
            whitePoints.map { it.toPair() },
            startPoint.toPair(),
        )

    private fun isDoubleFourFoul(
        blackPoints: Set<Point>,
        whitePoints: Set<Point>,
        startPoint: Point,
    ): Boolean =
        renjuRule.checkDoubleFourFoul(
            blackPoints.map { it.toPair() },
            whitePoints.map { it.toPair() },
            startPoint.toPair(),
        )

    private fun isOverlineFoul(
        blackPoints: Set<Point>,
        startPoint: Point,
    ): Boolean =
        renjuRule.checkOverline(
            blackPoints.map { it.toPair() },
            startPoint.toPair(),
        )

    private fun Point.toPair(): Pair<Int, Int> = row to col
}
