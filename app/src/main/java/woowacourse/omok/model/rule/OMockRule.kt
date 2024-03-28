package woowacourse.omok.model.rule

import woowacourse.omok.model.ruletype.RuleType
import woowacourse.omok.model.search.VisitedDirectionFirstClearResult
import woowacourse.omok.model.search.VisitedDirectionResult
import woowacourse.omok.model.player.BlackPlayer
import woowacourse.omok.model.player.Player

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
        const val MIN_IS_CLEAR_FOUR_TO_FOUR_COUNT = 2
        const val MIN_THREE_TO_THREE_COUNT = 4
        const val MIN_REVERSE_COUNT = 0
        const val MIN_O_MOCK_COUNT = 4
        const val EDGE_THREE_TO_THREE_COUNT = 2
        const val EDGE_FOUR_TO_FOUR_COUNT = 3
    }
}
