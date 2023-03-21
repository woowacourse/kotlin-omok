package domain.state

import domain.stone.Point
import domain.stone.Stones
import domain.rule.RuleAdapter

interface State {

    val stones: Stones
    fun put(point: Point, ruleAdapter: RuleAdapter = RuleAdapter(listOf())): State
}
