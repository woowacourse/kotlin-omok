package woowacourse.omok.domain.rule

import rule.facade.BlackRenjuRule
import woowacourse.omok.domain.Point
import woowacourse.omok.domain.stone.OmokStones
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.StoneColor

class RenjuRule(
    boardSize: Int,
) : ForbiddenMoveRule {
    private val blackRenjuRule = BlackRenjuRule(boardSize, boardSize)

    override fun checkViolation(
        stones: OmokStones,
        startStone: Stone,
    ): Violation =
        when (startStone.color) {
            StoneColor.BLACK -> {
                val blackPoints = stones.filter(StoneColor.BLACK)
                val whitePoints = stones.filter(StoneColor.WHITE)
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

    private fun OmokStones.filter(stoneColor: StoneColor): List<Point> = this.stones.filter { it.color == stoneColor }.map { it.point }

    private fun Point.toPair(): Pair<Int, Int> = row to col
}
