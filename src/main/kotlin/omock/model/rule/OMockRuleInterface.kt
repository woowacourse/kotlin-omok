package omock.model.rule

import omock.model.search.VisitedDirectionFirstClearResult
import omock.model.search.VisitedDirectionResult

interface OMockRuleInterface {
    fun checkRules(
        visitedDirectionResult: VisitedDirectionResult,
        visitedDirectionFirstClearResult: VisitedDirectionFirstClearResult,
    )
}
