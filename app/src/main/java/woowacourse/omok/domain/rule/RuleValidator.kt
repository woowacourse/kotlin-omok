package woowacourse.omok.domain.rule

import woowacourse.omok.domain.board.Board
import woowacourse.omok.domain.board.Point
import woowacourse.omok.domain.board.StoneColor
import woowacourse.omok.domain.rule.lib.CountInRowRule
import woowacourse.omok.domain.rule.lib.DoubleFourMoveRule
import woowacourse.omok.domain.rule.lib.DoubleThreeMoveRule
import woowacourse.omok.domain.rule.lib.OmokMoveRule
import woowacourse.omok.domain.rule.lib.OverlineRule

class RuleValidator {
    private val winningRules: MutableList<OmokMoveRule> =
        mutableListOf(
            CountInRowRule(StoneColor.BLACK.toInt()) { count -> count == OMOK_COUNT },
            CountInRowRule(StoneColor.WHITE.toInt()) { count -> count >= OMOK_COUNT },
        )

    private val violationRules: MutableList<OmokMoveRule> =
        mutableListOf(
            DoubleThreeMoveRule(StoneColor.BLACK.toInt()),
            DoubleFourMoveRule(StoneColor.BLACK.toInt()),
            OverlineRule(StoneColor.BLACK.toInt()),
        )

    fun checkWinCondition(
        board: Board,
        point: Point,
        stoneColor: StoneColor,
    ): Boolean = evaluateRules(winningRules, board, point, stoneColor)

    fun checkViolation(
        board: Board,
        point: Point,
        stoneColor: StoneColor,
    ): Boolean = evaluateRules(violationRules, board, point, stoneColor)

    private fun evaluateRules(
        rules: List<OmokMoveRule>,
        board: Board,
        point: Point,
        stoneColor: StoneColor,
    ): Boolean =
        rules
            .filter { it.currentStone == stoneColor.toInt() }
            .any { rule -> rule.validate(board.toList(), point.toOmokPosition()) }

    private fun Board.toList(): List<List<Int>> =
        List(size) { y ->
            List(size) { x ->
                points[Point(x + 1, y + 1)]?.toInt() ?: OmokMoveRule.EMPTY_STONE
            }
        }

    private fun Point.toOmokPosition(): Pair<Int, Int> = x - 1 to y - 1

    private fun StoneColor.toInt(): Int =
        when (this) {
            StoneColor.BLACK -> OmokMoveRule.BLACK_STONE
            StoneColor.WHITE -> OmokMoveRule.WHITE_STONE
            else -> OmokMoveRule.EMPTY_STONE
        }

    companion object {
        private const val OMOK_COUNT = 5
    }
}
