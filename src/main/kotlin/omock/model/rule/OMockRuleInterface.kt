package omock.model.rule

import omock.model.search.Direction
import omock.model.search.DirectionFirstClearResult
import omock.model.search.DirectionResult

interface OMockRuleInterface {
    fun checkRules(
        visited: Map<Direction, DirectionResult>,
        visitedFirstClear: Map<Direction, DirectionFirstClearResult>,
    )
}
