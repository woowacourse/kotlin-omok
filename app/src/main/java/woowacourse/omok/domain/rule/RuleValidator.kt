package woowacourse.omok.domain.rule

import woowacourse.omok.domain.board.Board
import woowacourse.omok.domain.board.Point
import woowacourse.omok.domain.board.StoneColor
import woowacourse.omok.domain.rule.count.FiveInRowRule
import woowacourse.omok.domain.rule.count.GameCountRuleAdapter
import woowacourse.omok.domain.rule.count.OverlineRule
import woowacourse.omok.domain.rule.lib.DoubleFourMoveRule
import woowacourse.omok.domain.rule.lib.DoubleThreeMoveRule
import woowacourse.omok.domain.rule.lib.ForbiddenMoveRuleAdapter

class RuleValidator {
    private val violationRules: MutableMap<GameRule, List<StoneColor>> = mutableMapOf()
    private val winningRules: MutableMap<GameRule, List<StoneColor>> = mutableMapOf()

    init {
        winningRules[GameCountRuleAdapter(FiveInRowRule())] = listOf(StoneColor.BLACK, StoneColor.WHITE)
        violationRules[ForbiddenMoveRuleAdapter(DoubleThreeMoveRule())] = listOf(StoneColor.BLACK)
        violationRules[ForbiddenMoveRuleAdapter(DoubleFourMoveRule())] = listOf(StoneColor.BLACK)
        violationRules[GameCountRuleAdapter(OverlineRule())] = listOf(StoneColor.BLACK)
    }

    fun addWinningRule(
        rule: GameRule,
        targetColors: List<StoneColor>,
    ) {
        winningRules[rule] = targetColors
    }

    fun addViolationRule(
        rule: GameRule,
        targetColors: List<StoneColor>,
    ) {
        violationRules[rule] = targetColors
    }

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
        rules: Map<GameRule, List<StoneColor>>,
        board: Board,
        point: Point,
        currentColor: StoneColor,
    ): Boolean {
        val applicableRules = rules.filter { it.value.contains(currentColor) }.keys
        return applicableRules.any { it.validateMove(board, point) }
    }
}
