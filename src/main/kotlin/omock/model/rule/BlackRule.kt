package omock.model.rule

import omock.model.CalculateType
import omock.model.CalculateType.Companion.checkCalculateType
import omock.model.Direction
import omock.model.Result
import omock.model.rule.OMockRule.Companion.EDGE_FOUR_TO_FOUR_COUNT
import omock.model.rule.OMockRule.Companion.EDGE_THREE_TO_THREE_COUNT
import omock.model.rule.OMockRule.Companion.INIT_COUNT
import omock.model.rule.OMockRule.Companion.MIN_FOUR_TO_FOUR_COUNT
import omock.model.rule.OMockRule.Companion.MIN_IS_CLEAR_FOUR_TO_FOUR_COUNT
import omock.model.rule.OMockRule.Companion.MIN_O_MOCK_COUNT
import omock.model.rule.OMockRule.Companion.MIN_REVERSE_COUNT
import omock.model.rule.OMockRule.Companion.MIN_THREE_TO_THREE_COUNT

class BlackRule : OMockRule {
    override fun judgementResult(visited: Map<Direction, Result>): Boolean {
        var threeToThreeCount = INIT_COUNT
        var fourToFourCount = INIT_COUNT
        var isReverseTwoAndThree = false
        var isClearFourToFourCount = INIT_COUNT
        visited.entries.forEach { (key, result) ->
            val isReverseResultFirstClear: Boolean = visited[Direction.reverse(key)]?.isFirstClear ?: false
            val reverseResultCount: Int = visited[Direction.reverse(key)]?.count ?: MIN_REVERSE_COUNT
            getCalculateType(
                isReverseResultFirstClear = isReverseResultFirstClear,
                reverseResultCount = reverseResultCount,
                result = result,
            ) { calculateType ->
                when (calculateType) {
                    CalculateType.FourToFourCount ->
                        CalculateType.FourToFourCount.checkCalculateType {
                            ++fourToFourCount >= MIN_FOUR_TO_FOUR_COUNT
                        }

                    CalculateType.IsClearFourToFourCount ->
                        CalculateType.IsClearFourToFourCount.checkCalculateType {
                            ++isClearFourToFourCount >= MIN_IS_CLEAR_FOUR_TO_FOUR_COUNT
                        }

                    CalculateType.IsReverseTwoAndThree ->
                        CalculateType.IsReverseTwoAndThree.checkCalculateType {
                            isReverseTwoAndThree = true
                            true
                        }

                    CalculateType.ThreeToThreeCount ->
                        CalculateType.ThreeToThreeCount.checkCalculateType {
                            ++threeToThreeCount >= MIN_THREE_TO_THREE_COUNT
                        }
                }
            }
            if (!isReverseTwoAndThree && (reverseResultCount + result.count >= MIN_O_MOCK_COUNT)) return true
        }
        return false
    }

    private inline fun getCalculateType(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        result: Result,
        type: (CalculateType) -> Unit,
    ) {
        if (!result.isLastClear) return
        if (!result.isFirstClear) {
            if (result.count + reverseResultCount == EDGE_THREE_TO_THREE_COUNT) {
                type(CalculateType.ThreeToThreeCount)
            } else if (result.count + reverseResultCount == EDGE_FOUR_TO_FOUR_COUNT) {
                type(CalculateType.FourToFourCount)
            } else if (result.count == EDGE_THREE_TO_THREE_COUNT &&
                reverseResultCount == EDGE_FOUR_TO_FOUR_COUNT &&
                !isReverseResultFirstClear
            ) {
                type(CalculateType.IsReverseTwoAndThree)
            }
        } else {
            if (isReverseResultFirstClear && result.count == EDGE_THREE_TO_THREE_COUNT) {
                type(CalculateType.IsClearFourToFourCount)
            } else {
                if (result.count + reverseResultCount == EDGE_THREE_TO_THREE_COUNT) type(CalculateType.IsClearFourToFourCount)
                if (reverseResultCount == EDGE_THREE_TO_THREE_COUNT) type(CalculateType.ThreeToThreeCount)
                if (reverseResultCount == EDGE_FOUR_TO_FOUR_COUNT) type(CalculateType.FourToFourCount)
            }
        }
    }
}
