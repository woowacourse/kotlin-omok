package domain.rule

import domain.Stone

class RuleAdapter(private val rules: List<Rule>) {
    fun checkStone(blackStones: Set<Stone>, whiteStones: Set<Stone>, nextStone: Stone) {
        rules.forEach {
            require(!it.checkRule(blackStones, whiteStones, nextStone)) { it.errorMessage }
        }
    }
}