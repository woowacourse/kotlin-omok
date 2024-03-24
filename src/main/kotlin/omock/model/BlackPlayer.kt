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
                    CalculateType.FourToFourCount -> checkFourToFour(++fourToFourCount)
                    CalculateType.IsClearFourToFourCount -> checkIsClearFourToFour(++isClearFourToFourCount)
                    CalculateType.IsReverseTwoAndThree -> isReverseTwoAndThree = checkIsReverseTwoAndThree()
                    CalculateType.ThreeToThreeCount -> checkThreeToThreeCount(++threeToThreeCount)
                }
            }
            if (isOMockWinState(!isReverseTwoAndThree, reverseResultCount + result.count)) return true
        }
        return false
    }

    private fun isOMockWinState(
        isNotReverseTwoAndThree: Boolean,
        totalCount: Int,
    ): Boolean {
        return isNotReverseTwoAndThree && isOverMinOMockCount(totalCount)
    }

    private fun isOverMinOMockCount(count: Int): Boolean {
        return count >= MIN_O_MOCK_COUNT
    }

    private fun checkFourToFour(fourToFourCount: Int) {
        CalculateType.FourToFourCount.checkCalculateType {
            fourToFourCount >= MIN_FOUR_TO_FOUR_COUNT
        }
    }

    private fun checkIsClearFourToFour(isClearFourToFourCount: Int) {
        CalculateType.IsClearFourToFourCount.checkCalculateType {
            isClearFourToFourCount >= MIN_IS_CLEAR_FOUR_TO_FOUR_COUNT
        }
    }

    private fun checkIsReverseTwoAndThree(): Boolean {
        var isReverseTwoAndThree = false
        CalculateType.IsReverseTwoAndThree.checkCalculateType {
            isReverseTwoAndThree = true
            true
        }
        return isReverseTwoAndThree
    }

    private fun checkThreeToThreeCount(threeToThreeCount: Int) {
        CalculateType.ThreeToThreeCount.checkCalculateType { threeToThreeCount >= MIN_THREE_TO_THREE_COUNT }
    }

    private inline fun getCalculateType(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        directionResult: DirectionResult,
        type: (CalculateType) -> Unit,
    ) {
        if (isNotLastClearResult(directionResult)) return
        if (isNotFirstClearResult(directionResult)) {
            provideFirstClearResult(
                isReverseResultFirstClear = isReverseResultFirstClear,
                reverseResultCount = reverseResultCount,
                directionResult = directionResult,
                type = type,
            )
        } else {
            provideNotFirstClearResult(
                isReverseResultFirstClear = isReverseResultFirstClear,
                reverseResultCount = reverseResultCount,
                directionResult = directionResult,
                type = type,
            )
        }
    }

    private inline fun provideFirstClearResult(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        directionResult: DirectionResult,
        type: (CalculateType) -> Unit,
    ) {
        if (isThreeToThreeCount(directionResult.count + reverseResultCount)) {
            type(CalculateType.ThreeToThreeCount)
        } else if (isFourToFourCount(directionResult.count + reverseResultCount)) {
            type(CalculateType.FourToFourCount)
        } else if (isThreeToThreeCount(directionResult.count) &&
            isFourToFourCount(reverseResultCount) && !isReverseResultFirstClear
        ) {
            type(CalculateType.IsReverseTwoAndThree)
        }
    }

    private inline fun provideNotFirstClearResult(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        directionResult: DirectionResult,
        type: (CalculateType) -> Unit,
    ) {
        if (isReverseResultFirstClear && isThreeToThreeCount(directionResult.count)) {
            type(CalculateType.IsClearFourToFourCount)
        } else {
            if (isThreeToThreeCount(directionResult.count + reverseResultCount)) type(CalculateType.IsClearFourToFourCount)
            if (isThreeToThreeCount(reverseResultCount)) type(CalculateType.ThreeToThreeCount)
            if (isFourToFourCount(reverseResultCount)) type(CalculateType.FourToFourCount)
        }
    }

    private fun isNotLastClearResult(directionResult: DirectionResult): Boolean {
        return !directionResult.isLastClear
    }

    private fun isNotFirstClearResult(directionResult: DirectionResult): Boolean {
        return !directionResult.isFirstClear
    }

    private fun isThreeToThreeCount(count: Int): Boolean {
        return count == EDGE_THREE_TO_THREE_COUNT
    }

    private fun isFourToFourCount(count: Int): Boolean {
        return count == EDGE_FOUR_TO_FOUR_COUNT
    }
}
