package woowacourse.omok.domain.rule

import woowacourse.omok.domain.board.Board
import woowacourse.omok.domain.board.CellState
import woowacourse.omok.domain.board.Point
import woowacourse.omok.domain.rule.lib.CountInRowRule
import woowacourse.omok.domain.rule.lib.DoubleFourMoveRule
import woowacourse.omok.domain.rule.lib.DoubleThreeMoveRule
import woowacourse.omok.domain.rule.lib.OmokMoveRule
import woowacourse.omok.domain.rule.lib.OverlineRule

class RuleValidator {
    private val winningRules: MutableList<OmokMoveRule> =
        mutableListOf(
            CountInRowRule(CellState.BLACK.toInt()) { count -> count == OMOK_COUNT },
            CountInRowRule(CellState.WHITE.toInt()) { count -> count >= OMOK_COUNT },
        )

    private val violationRules: MutableList<OmokMoveRule> =
        mutableListOf(
            DoubleThreeMoveRule(CellState.BLACK.toInt()),
            DoubleFourMoveRule(CellState.BLACK.toInt()),
            OverlineRule(CellState.BLACK.toInt()),
        )

    fun checkWinCondition(
        board: Board,
        point: Point,
        state: CellState,
    ): Boolean = evaluateRules(winningRules, board, point, state)

    fun checkViolation(
        board: Board,
        point: Point,
        state: CellState,
    ): Boolean = evaluateRules(violationRules, board, point, state)

    private fun evaluateRules(
        rules: List<OmokMoveRule>,
        board: Board,
        point: Point,
        state: CellState,
    ): Boolean =
        rules
            .filter { it.currentStone == state.toInt() }
            .any { rule -> rule.validate(board.toList(), point.toOmokPosition()) }

    private fun Board.toList(): List<List<Int>> =
        List(size) { y ->
            List(size) { x ->
                cells[Point(x + 1, y + 1)]?.toInt() ?: OmokMoveRule.EMPTY_STONE
            }
        }

    private fun Point.toOmokPosition(): Pair<Int, Int> = x - 1 to y - 1

    private fun CellState.toInt(): Int =
        when (this) {
            CellState.BLACK -> OmokMoveRule.BLACK_STONE
            CellState.WHITE -> OmokMoveRule.WHITE_STONE
            else -> OmokMoveRule.EMPTY_STONE
        }

    companion object {
        private const val OMOK_COUNT = 5
    }
}
