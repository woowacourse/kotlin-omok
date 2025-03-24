package omok.model.rule

import omok.model.board.Board
import omok.model.board.Point
import omok.model.board.StoneColor

class RuleValidator {
    private val violationRules: MutableMap<OmokRule, List<StoneColor>> = mutableMapOf()
    private val winningRules: MutableMap<OmokRule, List<StoneColor>> = mutableMapOf()

    fun addWinningRule(
        rule: OmokRule,
        targetColors: List<StoneColor>,
    ) {
        winningRules[rule] = targetColors
    }

    fun addViolationRule(
        rule: OmokRule,
        targetColors: List<StoneColor>,
    ) {
        violationRules[rule] = targetColors
    }

    fun checkWinCondition(
        board: Board,
        point: Point,
        stoneColor: StoneColor,
    ): Boolean {
        return evaluateRules(winningRules, board, point, stoneColor)
    }

    fun checkViolation(
        board: Board,
        point: Point,
        stoneColor: StoneColor,
    ): Boolean {
        return evaluateRules(violationRules, board, point, stoneColor)
    }

    private fun evaluateRules(
        rules: Map<OmokRule, List<StoneColor>>,
        board: Board,
        point: Point,
        currentColor: StoneColor,
    ): Boolean {
        val applicableRules = rules.filter { it.value.contains(currentColor) }.keys
        return applicableRules.any { it.validateMove(board, point) }
    }
}
