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
    rules: List<OmokMoveRule> = defaultRules(),
) {
    private val rules: MutableList<OmokMoveRule> = rules.toMutableList()

    fun checkRules(
        board: Board,
        point: Point,
        state: CellState,
        isWin: Boolean,
    ): Boolean =
        evaluateRules(
            rules.filter { it.isWinningRule == isWin },
            board,
            point,
            state,
        )

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

        private fun defaultRules(): List<OmokMoveRule> =
            listOf(
                CountInRowRule(OmokMoveRule.BLACK_STONE, true) { it == OMOK_COUNT },
                CountInRowRule(OmokMoveRule.WHITE_STONE, true) { it >= OMOK_COUNT },
                DoubleThreeMoveRule(OmokMoveRule.BLACK_STONE, false),
                DoubleFourMoveRule(OmokMoveRule.BLACK_STONE, false),
                OverlineRule(OmokMoveRule.BLACK_STONE, false),
            )
    }
}
