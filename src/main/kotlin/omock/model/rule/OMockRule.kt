package omock.model.rule

import omock.model.player.BlackPlayer
import omock.model.player.Player
import omock.model.ruletype.RuleType
import omock.model.search.VisitedDirectionFirstClearResult
import omock.model.search.VisitedDirectionResult

class OMockRule(private val ruleTypes: List<RuleType>) : OMockRuleInterface {
    override fun checkRules(
        visitedDirectionResult: VisitedDirectionResult,
        visitedDirectionFirstClearResult: VisitedDirectionFirstClearResult,
    ) {
        ruleTypes.forEach {
            it.checkRule(visitedDirectionResult, visitedDirectionFirstClearResult)
        }
    }

    fun checkPlayerRules(
        player: Player,
        visitedDirectionResult: VisitedDirectionResult,
        visitedDirectionFirstClearResult: VisitedDirectionFirstClearResult,
    ) {
        if (player is BlackPlayer) {
            checkRules(visitedDirectionResult, visitedDirectionFirstClearResult)
        }
    }

    companion object {
        const val INIT_COUNT = 0
        const val MIN_FOUR_TO_FOUR_COUNT = 4
        const val MIN_IS_CLEAR_FOUR_TO_FOUR_COUNT = 4
        const val MIN_THREE_TO_THREE_COUNT = 4
        const val MIN_REVERSE_COUNT = 0
        const val MIN_O_MOCK_COUNT = 4
        const val EDGE_THREE_TO_THREE_COUNT = 2
        const val EDGE_FOUR_TO_FOUR_COUNT = 3
    }
}
