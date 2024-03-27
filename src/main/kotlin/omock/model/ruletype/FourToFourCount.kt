package omock.model.ruletype

import omock.model.rule.OMockRule
import omock.model.rule.OMockRule.Companion.MIN_FOUR_TO_FOUR_COUNT
import omock.model.ruletype.RuleType.Companion.checkCalculateType
import omock.model.search.Direction
import omock.model.search.DirectionResult
import omock.model.search.VisitedDirectionFirstClearResult
import omock.model.search.VisitedDirectionResult

data object FourToFourCount : RuleType {
    private const val FOUR_TO_FOUR_COUNT_MESSAGE = "4-4 금수를 어겼습니다."

    override fun checkRule(
        visitedDirectionResult: VisitedDirectionResult,
        visitedDirectionFirstClearResult: VisitedDirectionFirstClearResult
    ) {
        var count = OMockRule.INIT_COUNT
        visitedDirectionResult.visited.entries.forEach { (key, result) ->
            val isReverseResultFirstClear: Boolean =
                visitedDirectionFirstClearResult.visitedFirstClear[Direction.reverse(key)]?.isFirstClear ?: false
            val reverseResultCount: Int =
                visitedDirectionResult.visited[Direction.reverse(key)]?.count ?: OMockRule.MIN_REVERSE_COUNT
            if (isLastClearResult(result)) {
                if (
                    isCalculateType(
                        isReverseResultFirstClear = isReverseResultFirstClear,
                        reverseResultCount = reverseResultCount,
                        directionResult = result,
                    )
                ) count++
            }
        }
        checkFourToFour(count)
    }

    override fun isCalculateType(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        directionResult: DirectionResult
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
        directionResult: DirectionResult
    ): Boolean {
        return isFourToFourCount(directionResult.count)
    }

    override fun provideNotFirstClearResult(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        directionResult: DirectionResult
    ): Boolean {
        return isFourToFourCount(directionResult.count + reverseResultCount)
    }

    override fun getCalculateMessage(ruleType: RuleType): String {
        return FOUR_TO_FOUR_COUNT_MESSAGE
    }

    private fun checkFourToFour(fourToFourCount: Int) {
        FourToFourCount.checkCalculateType {
            fourToFourCount >= MIN_FOUR_TO_FOUR_COUNT
        }
    }
}
