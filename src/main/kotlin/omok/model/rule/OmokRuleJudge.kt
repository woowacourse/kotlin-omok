package omok.model.rule

import omok.model.board.Board
import omok.model.board.Point
import omok.model.board.StoneColor
import omok.model.rule.count.OmokCountRule
import omok.model.rule.count.OverlineRule
import omok.model.rule.lib.DoubleFourMoveRule
import omok.model.rule.lib.DoubleThreeMoveRule
import omok.model.rule.lib.ForbiddenMoveRule

class OmokRuleJudge {
    private val rules: MutableMap<OmokRule, List<StoneColor>> = mutableMapOf()
    private val winningRules: MutableMap<OmokRule, List<StoneColor>> = mutableMapOf()

    fun applyWinningRule(rule: OmokRule) {
        winningRules[rule] = listOf(StoneColor.BLACK, StoneColor.WHITE)
    }

    fun applyRenjuRule() {
        rules +=
            mapOf(
                DoubleThreeMoveRule() to listOf(StoneColor.BLACK),
                DoubleFourMoveRule() to listOf(StoneColor.BLACK),
                OverlineRule() to listOf(StoneColor.BLACK),
            )
    }

    fun isWin(
        board: Board,
        previousPoint: Point,
        currentColor: StoneColor,
    ): Boolean {
        val result = checkRules(winningRules, board, previousPoint, currentColor)
        return result
    }

    fun validate(
        board: Board,
        previousPoint: Point,
        currentColor: StoneColor,
    ): Boolean {
        val result = checkRules(rules, board, previousPoint, currentColor)
        return result
    }

    private fun checkRules(
        ruleSet: Map<OmokRule, List<StoneColor>>,
        board: Board,
        previousPoint: Point,
        currentColor: StoneColor,
    ): Boolean {
        val applicableRules = ruleSet.filterKeys { it in ruleSet && currentColor in ruleSet[it]!! }.keys
        val (convertedBoard, convertedPoint) = converteMove(board, previousPoint)
        return applicableRules.none {
            when (it) {
                is OmokCountRule -> it.calculate(board, previousPoint)
                is ForbiddenMoveRule -> it.validate(convertedBoard, convertedPoint)
                else -> false
            }
        }
    }

    private fun converteMove(
        board: Board,
        previousPoint: Point,
    ): Pair<List<List<Int>>, Pair<Int, Int>> {
        return OmokConverter.converteOmokBoard(board) to OmokConverter.converteOmokPoint(previousPoint)
    }
}
