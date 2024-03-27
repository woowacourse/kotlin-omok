package omock.model.ruletype

import omock.model.rule.OMockRule
import omock.model.search.DirectionResult
import omock.model.search.VisitedDirectionFirstClearResult
import omock.model.search.VisitedDirectionResult

sealed interface RuleType {
    fun checkRule(
        visitedDirectionResult: VisitedDirectionResult,
        visitedDirectionFirstClearResult: VisitedDirectionFirstClearResult,
    )

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
        inline fun RuleType.checkCalculateType(action: () -> Boolean) {
            if (action()) {
                throw IllegalArgumentException(getCalculateMessage(this))
            }
        }
    }
}
