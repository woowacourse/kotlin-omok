package omock.model.ruletype

import omock.model.rule.OMockRule
import omock.model.ruletype.RuleType.Companion.checkCalculateType
import omock.model.search.Direction
import omock.model.search.DirectionResult
import omock.model.search.VisitedDirectionFirstClearResult
import omock.model.search.VisitedDirectionResult

data object ThreeToThreeCount : RuleType {
    private const val THREE_TO_THREE_COUNT_MESSAGE = "3-3 금수를 어겼습니다."

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
        checkThreeToThreeCount(count)
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
        return isThreeToThreeCount(directionResult.count)
    }

    override fun provideNotFirstClearResult(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        directionResult: DirectionResult
    ): Boolean {
        return isThreeToThreeCount(directionResult.count + reverseResultCount)
    }

    private fun checkThreeToThreeCount(threeToThreeCount: Int) {
        ThreeToThreeCount.checkCalculateType { threeToThreeCount >= OMockRule.MIN_THREE_TO_THREE_COUNT }
    }

    override fun getCalculateMessage(ruleType: RuleType): String {
        return THREE_TO_THREE_COUNT_MESSAGE
    }
}
