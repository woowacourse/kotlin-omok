package domain.state

import domain.Stone
import domain.rule.RuleAdapter

interface State {

    val blackStones: Set<Stone>
    val whiteStones: Set<Stone>

    fun put(stone: Stone, ruleAdapter: RuleAdapter = RuleAdapter(listOf())): State
}
