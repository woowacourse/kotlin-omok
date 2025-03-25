package omok.domain.rule

import omok.domain.Point
import omok.domain.stone.Stone
import omok.domain.stone.StoneColor
import rule.facade.BlackRenjuRule

class RenjuRule(
    boardSize: Int,
) {
    private val blackRenjuRule = BlackRenjuRule(boardSize, boardSize)

    fun checkViolation(
        stones: Set<Stone>,
        startStone: Stone,
    ): Violation =
        when (startStone.color) {
            StoneColor.BLACK -> {
                val blackPoints = stones.filter { it.color == StoneColor.BLACK }.map { it.point }
                val whitePoints = stones.filter { it.color == StoneColor.WHITE }.map { it.point }
                checkBlackViolation(blackPoints, whitePoints, startStone.point)
            }
            StoneColor.WHITE -> Violation.NONE
        }

    private fun checkBlackViolation(
        blackPoints: List<Point>,
        whitePoints: List<Point>,
        startPoint: Point,
    ): Violation =
        when {
            isDoubleFourFoul(blackPoints, whitePoints, startPoint) -> Violation.DOUBLE_FOUR
            isDoubleThreeFoul(blackPoints, whitePoints, startPoint) -> Violation.DOUBLE_THREE
            isOverlineFoul(blackPoints, startPoint) -> Violation.OVERLINE
            else -> Violation.NONE
        }

    private fun isDoubleThreeFoul(
        blackPoints: List<Point>,
        whitePoints: List<Point>,
        startPoint: Point,
    ): Boolean =
        blackRenjuRule.checkDoubleThreeFoul(
            blackPoints.map { it.toPair() },
            whitePoints.map { it.toPair() },
            startPoint.toPair(),
        )

    private fun isDoubleFourFoul(
        blackPoints: List<Point>,
        whitePoints: List<Point>,
        startPoint: Point,
    ): Boolean =
        blackRenjuRule.checkDoubleFourFoul(
            blackPoints.map { it.toPair() },
            whitePoints.map { it.toPair() },
            startPoint.toPair(),
        )

    private fun isOverlineFoul(
        blackPoints: List<Point>,
        startPoint: Point,
    ): Boolean =
        blackRenjuRule.checkOverline(
            blackPoints.map { it.toPair() },
            startPoint.toPair(),
        )

    private fun Point.toPair(): Pair<Int, Int> = row to col
}
