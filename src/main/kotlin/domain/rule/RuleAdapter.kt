package domain.rule

import domain.Stone
import domain.Stones

class RuleAdapter(private val rules: List<Rule>) {
    fun checkStone(stones: Stones, justPlacedStone: Stone) {
        rules.forEach {
            require(!it.checkRule(stones, justPlacedStone)) { it.errorMessage }
        }
    }
}