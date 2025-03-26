package woowacourse.omok.model.rule

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Point
import woowacourse.omok.model.board.StoneColor
import woowacourse.omok.model.rule.count.FiveInRowRule
import woowacourse.omok.model.rule.count.GameCountRuleAdapter
import woowacourse.omok.model.rule.count.OverlineRule
import woowacourse.omok.model.rule.lib.DoubleFourMoveRule
import woowacourse.omok.model.rule.lib.DoubleThreeMoveRule
import woowacourse.omok.model.rule.lib.ForbiddenMoveRuleAdapter

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
