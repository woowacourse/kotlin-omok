package omock.model.ruletype

import omock.model.rule.OMockRule
import omock.model.ruletype.RuleType.Companion.checkCalculateType
import omock.model.search.Direction
import omock.model.search.DirectionResult
import omock.model.search.VisitedDirectionFirstClearResult
import omock.model.search.VisitedDirectionResult

data object IsClearFourToFourCount : RuleType {
    private const val IS_CLEAR_FOUR_TO_FOUR_COUNT_MESSAGE = "열린 4-4 금수를 어겼습니다."

    override fun checkRule(
        visitedDirectionResult: VisitedDirectionResult,
        visitedDirectionFirstClearResult: VisitedDirectionFirstClearResult,
    ) {
        var count = OMockRule.INIT_COUNT
        visitedDirectionResult.visited.entries.forEach { (key, result) ->
            val isReverseResultFirstClear: Boolean =
                visitedDirectionFirstClearResult.visitedFirstClear[Direction.reverse(key)]?.isFirstClear ?: false
            val reverseResultCount: Int =
                visitedDirectionResult.visited[Direction.reverse(key)]?.count ?: OMockRule.MIN_REVERSE_COUNT
            val isResultFirstClear: Boolean =
                visitedDirectionFirstClearResult.visitedFirstClear[key]?.isFirstClear ?: false
            if (isLastClearResult(result)) {
                if (
                    isCalculateType(
                        isReverseResultFirstClear = isReverseResultFirstClear,
                        reverseResultCount = reverseResultCount,
                        directionResult = result,
                    ) && isResultFirstClear
                ) {
                    count++
                }
            }
        }
        checkIsClearFourToFour(count)
    }

    override fun isCalculateType(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        directionResult: DirectionResult,
    ): Boolean {
        return if (isNotFirstClearResult(isReverseResultFirstClear)) {
            provideNotFirstClearResult(
                isReverseResultFirstClear = isReverseResultFirstClear,
                reverseResultCount = reverseResultCount,
                directionResult = directionResult,
            )
        } else {
            provideFirstClearResult(
                isReverseResultFirstClear = isReverseResultFirstClear,
                reverseResultCount = reverseResultCount,
                directionResult = directionResult,
            )
        }
    }

    override fun provideFirstClearResult(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        directionResult: DirectionResult,
    ): Boolean {
        return false
    }

    override fun provideNotFirstClearResult(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        directionResult: DirectionResult,
    ): Boolean {
        return isThreeToThreeCount(directionResult.count)
    }

    override fun getCalculateMessage(ruleType: RuleType): String {
        return IS_CLEAR_FOUR_TO_FOUR_COUNT_MESSAGE
    }

    private fun checkIsClearFourToFour(isClearFourToFourCount: Int) {
        IsClearFourToFourCount.checkCalculateType {
            isClearFourToFourCount >= OMockRule.MIN_IS_CLEAR_FOUR_TO_FOUR_COUNT
        }
    }
}
