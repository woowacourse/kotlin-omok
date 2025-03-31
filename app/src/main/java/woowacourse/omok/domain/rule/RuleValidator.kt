package woowacourse.omok.domain.rule

import woowacourse.omok.domain.board.Board
import woowacourse.omok.domain.board.CellState
import woowacourse.omok.domain.board.Point
import woowacourse.omok.domain.rule.lib.CountInRowRule
import woowacourse.omok.domain.rule.lib.DoubleFourMoveRule
import woowacourse.omok.domain.rule.lib.DoubleThreeMoveRule
import woowacourse.omok.domain.rule.lib.OmokMoveRule
import woowacourse.omok.domain.rule.lib.OverlineRule

class RuleValidator(
    winningRules: List<OmokMoveRule> = defaultWinningRule(),
    violationRules: List<OmokMoveRule> = defaultViolationRule(),
) {
    private val winningRules: List<OmokMoveRule> = winningRules.toMutableList()
    private val violationRules: MutableList<OmokMoveRule> = violationRules.toMutableList()

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
    ): Boolean {
        val stone = state.toInt()
        return rules
            .filter { it.currentStone == stone }
            .any { it.validate(board.toList(), point.toOmokPosition()) }
    }

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

        private fun defaultWinningRule(): List<OmokMoveRule> =
            listOf(
                CountInRowRule(OmokMoveRule.BLACK_STONE) { it == OMOK_COUNT },
                CountInRowRule(OmokMoveRule.WHITE_STONE) { it >= OMOK_COUNT },
            )

        private fun defaultViolationRule(): List<OmokMoveRule> =
            listOf(
                DoubleThreeMoveRule(OmokMoveRule.BLACK_STONE),
                DoubleFourMoveRule(OmokMoveRule.BLACK_STONE),
                OverlineRule(OmokMoveRule.BLACK_STONE),
            )
    }
}
