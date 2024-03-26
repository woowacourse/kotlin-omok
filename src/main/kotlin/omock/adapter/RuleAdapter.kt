package omock.adapter

import core.omock.rule.OMockRule
import omock.model.ColumnStates

class RuleAdapter(private val rules: List<OMockRule> = emptyList()) {
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
        return OMockRule.isGameWon(ruleBoard, row, column)
    }

    fun convertToInteger(stoneStates: List<ColumnStates>): RuleAdapter {
        ruleBoard = stoneStates.map { columnStates -> columnStates.getStoneNumber() }
        return this
    }
}
