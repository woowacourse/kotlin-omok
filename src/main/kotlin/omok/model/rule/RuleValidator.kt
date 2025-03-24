package omok.model.rule

import omok.model.board.Board
import omok.model.board.Point
import omok.model.board.StoneColor

class RuleValidator {
    private val violationRules: MutableMap<GameRule, List<StoneColor>> = mutableMapOf()
    private val winningRules: MutableMap<GameRule, List<StoneColor>> = mutableMapOf()

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
        rules: Map<GameRule, List<StoneColor>>,
        board: Board,
        point: Point,
        currentColor: StoneColor,
    ): Boolean {
        val applicableRules = rules.filter { it.value.contains(currentColor) }.keys
        return applicableRules.any { it.validateMove(board, point) }
    }
}
