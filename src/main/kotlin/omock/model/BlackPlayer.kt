package omock.model

import omock.model.CalculateType.Companion.checkCalculateType

data class BlackPlayer(
    override val stoneHistory: ArrayDeque<Stone> = ArrayDeque(),
) : Player() {
    override fun judgementResult(visited: Map<Direction, DirectionResult>): Boolean {
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
                directionResult = result,
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
                        CalculateType.ThreeToThreeCount.checkCalculateType { ++threeToThreeCount >= MIN_THREE_TO_THREE_COUNT }
                }
            }
            if (!isReverseTwoAndThree && (reverseResultCount + result.count >= MIN_O_MOCK_COUNT)) return true
        }
        return false
    }

    private inline fun getCalculateType(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        directionResult: DirectionResult,
        type: (CalculateType) -> Unit,
    ) {
        if (!directionResult.isLastClear) return
        if (!directionResult.isFirstClear) {
            if (directionResult.count + reverseResultCount == EDGE_THREE_TO_THREE_COUNT) {
                type(CalculateType.ThreeToThreeCount)
            } else if (directionResult.count + reverseResultCount == EDGE_FOUR_TO_FOUR_COUNT) {
                type(CalculateType.FourToFourCount)
            } else if (directionResult.count == EDGE_THREE_TO_THREE_COUNT &&
                reverseResultCount == EDGE_FOUR_TO_FOUR_COUNT && !isReverseResultFirstClear
            ) {
                type(
                    CalculateType.IsReverseTwoAndThree,
                )
            }
        } else {
            if (isReverseResultFirstClear && directionResult.count == EDGE_THREE_TO_THREE_COUNT) {
                type(CalculateType.IsClearFourToFourCount)
            } else {
                if (directionResult.count + reverseResultCount == EDGE_THREE_TO_THREE_COUNT) type(CalculateType.IsClearFourToFourCount)
                if (reverseResultCount == EDGE_THREE_TO_THREE_COUNT) type(CalculateType.ThreeToThreeCount)
                if (reverseResultCount == EDGE_FOUR_TO_FOUR_COUNT) type(CalculateType.FourToFourCount)
            }
        }
    }
}
