package woowacourse.omok.model.ruletype

import woowacourse.omok.model.GameState
import woowacourse.omok.model.rule.OMockRule
import woowacourse.omok.model.search.DirectionResult
import woowacourse.omok.model.search.VisitedDirectionFirstClearResult
import woowacourse.omok.model.search.VisitedDirectionResult

sealed interface RuleType {
    fun checkRule(
        visitedDirectionResult: VisitedDirectionResult,
        visitedDirectionFirstClearResult: VisitedDirectionFirstClearResult,
    ): GameState.CheckRuleTypeState

    fun isCalculateType(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        directionResult: DirectionResult,
    ): Boolean

    fun isLastClearResult(directionResult: DirectionResult): Boolean {
        return directionResult.isLastClear
    }

    fun isNotFirstClearResult(isReverseResultFirstClear: Boolean): Boolean {
        return !isReverseResultFirstClear
    }

    fun provideFirstClearResult(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        directionResult: DirectionResult,
    ): Boolean

    fun provideNotFirstClearResult(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        directionResult: DirectionResult,
    ): Boolean

    fun isFourToFourCount(count: Int): Boolean {
        return count == OMockRule.EDGE_FOUR_TO_FOUR_COUNT
    }

    fun isThreeToThreeCount(count: Int): Boolean {
        return count == OMockRule.EDGE_THREE_TO_THREE_COUNT
    }

    fun getCalculateMessage(ruleType: RuleType): String

    companion object {
        inline fun RuleType.checkCalculateType(action: () -> Boolean) : GameState.CheckRuleTypeState{
            if (action()) {
                return GameState.CheckRuleTypeState.Failure(Throwable(getCalculateMessage(this)))
            }
            return GameState.CheckRuleTypeState.Success
        }
    }
}
