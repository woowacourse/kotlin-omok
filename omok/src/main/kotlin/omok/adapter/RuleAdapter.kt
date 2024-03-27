package omok.adapter

import core.omok.rule.OMokRule
import omok.model.ColumnStates

class RuleAdapter(private val rules: List<OMokRule> = emptyList()) {
    private lateinit var ruleBoard: List<List<Int>>

    fun validPosition(
        row: Int,
        column: Int,
    ): Boolean {
        return rules.any { oMockRule ->
            oMockRule.validPosition(ruleBoard, row, column)
        }
    }

    fun isGameWon(
        row: Int,
        column: Int,
    ): Boolean {
        return OMokRule.isGameWon(ruleBoard, row, column)
    }

    fun convertToInteger(stoneStates: List<ColumnStates>): RuleAdapter {
        ruleBoard = stoneStates.map { columnStates -> columnStates.getStoneNumber() }
        return this
    }
}
