package omock.model.ruletype

import omock.model.rule.OMockRule
import omock.model.ruletype.RuleType.Companion.checkCalculateType
import omock.model.search.Direction
import omock.model.search.DirectionResult
import omock.model.search.VisitedDirectionFirstClearResult
import omock.model.search.VisitedDirectionResult

data object IsReverseTwoAndThree : RuleType {
    private const val IS_REVERSE_TWO_AND_THREE_MESSAGE = "장목 금수를 어겼습니다."

    override fun checkRule(
        visitedDirectionResult: VisitedDirectionResult,
        visitedDirectionFirstClearResult: VisitedDirectionFirstClearResult
    ) {
        visitedDirectionResult.visited.entries.forEach { (key, result) ->
            val isReverseResultFirstClear: Boolean =
                visitedDirectionFirstClearResult.visitedFirstClear[Direction.reverse(key)]?.isFirstClear ?: false
            val reverseResultCount: Int =
                visitedDirectionResult.visited[Direction.reverse(key)]?.count ?: OMockRule.MIN_REVERSE_COUNT
            if (isLastClearResult(result)) {
                checkIsReverseTwoAndThree (
                    isCalculateType(
                        isReverseResultFirstClear = isReverseResultFirstClear,
                        reverseResultCount = reverseResultCount,
                        directionResult = result,
                    )
                )
            }
        }
    }

    override fun isCalculateType(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        directionResult: DirectionResult
    ): Boolean {
        return provideFirstClearResult(
            isReverseResultFirstClear = isReverseResultFirstClear,
            reverseResultCount = reverseResultCount,
            directionResult = directionResult,
        ) && provideNotFirstClearResult(
            isReverseResultFirstClear = isReverseResultFirstClear,
            reverseResultCount = reverseResultCount,
            directionResult = directionResult,
        )
    }

    override fun provideFirstClearResult(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        directionResult: DirectionResult
    ): Boolean {
        return isThreeToThreeCount(directionResult.count) &&
                isFourToFourCount(reverseResultCount) && !isReverseResultFirstClear
    }

    override fun provideNotFirstClearResult(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        directionResult: DirectionResult
    ): Boolean {
        return true
    }

    override fun getCalculateMessage(ruleType: RuleType): String {
        return IS_REVERSE_TWO_AND_THREE_MESSAGE
    }

    private fun checkIsReverseTwoAndThree(result : Boolean) {
        IsReverseTwoAndThree.checkCalculateType { result }
    }
}
