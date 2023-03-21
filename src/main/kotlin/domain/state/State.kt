package domain.state

import domain.Point
import domain.Stones
import domain.rule.RuleAdapter

interface State {

    val stones: Stones
    fun put(point: Point, ruleAdapter: RuleAdapter = RuleAdapter(listOf())): State
}
