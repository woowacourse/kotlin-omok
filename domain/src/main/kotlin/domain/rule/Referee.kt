package domain.rule

import domain.stone.Stone
import domain.stone.Stones

class Referee(private val rules: List<Rule>) {
    fun checkStone(stones: Stones, justPlacedStone: Stone) {
        rules.forEach {
            require(!it.checkRule(stones, justPlacedStone)) { it.errorMessage }
        }
    }
}