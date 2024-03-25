package omock.model.rule

import omock.model.CalculateType
import omock.model.CalculateType.Companion.checkCalculateType
import omock.model.player.Player
import omock.model.search.Direction
import omock.model.search.DirectionResult

class OMockRule: OMockRuleInterface {

    override fun checkRules(visited: Map<Direction, DirectionResult>) {
        var threeToThreeCount = Player.INIT_COUNT
        var fourToFourCount = Player.INIT_COUNT
        var isClearFourToFourCount = Player.INIT_COUNT
        visited.entries.forEach { (key, result) ->
            val isReverseResultFirstClear: Boolean = visited[Direction.reverse(key)]?.isFirstClear ?: false
            val reverseResultCount: Int = visited[Direction.reverse(key)]?.count ?: Player.MIN_REVERSE_COUNT
            if (isLastClearResult(result)) {
                getCalculateType(
                    isReverseResultFirstClear = isReverseResultFirstClear,
                    reverseResultCount = reverseResultCount,
                    directionResult = result,
                ).forEach { calculateType ->
                    when (calculateType) {
                        CalculateType.FourToFourCount -> checkFourToFour(++fourToFourCount)
                        CalculateType.IsClearFourToFourCount -> checkIsClearFourToFour(++isClearFourToFourCount)
                        CalculateType.IsReverseTwoAndThree ->  checkIsReverseTwoAndThree()
                        CalculateType.ThreeToThreeCount -> checkThreeToThreeCount(++threeToThreeCount)
                    }
                }
            }
        }
    }

    private fun checkFourToFour(fourToFourCount: Int) {
        CalculateType.FourToFourCount.checkCalculateType {
            fourToFourCount >= Player.MIN_FOUR_TO_FOUR_COUNT
        }
    }

    private fun checkIsClearFourToFour(isClearFourToFourCount: Int) {
        CalculateType.IsClearFourToFourCount.checkCalculateType {
            isClearFourToFourCount >= Player.MIN_IS_CLEAR_FOUR_TO_FOUR_COUNT
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
        CalculateType.ThreeToThreeCount.checkCalculateType { threeToThreeCount >= Player.MIN_THREE_TO_THREE_COUNT }
    }

    private fun getCalculateType(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        directionResult: DirectionResult,
    ): List<CalculateType> {
        val calculateTypes: MutableList<CalculateType> = mutableListOf()
        if (isNotFirstClearResult(directionResult)) {
            provideFirstClearResult(
                isReverseResultFirstClear = isReverseResultFirstClear,
                reverseResultCount = reverseResultCount,
                directionResult = directionResult,
            )?.let { calculateType ->
                calculateTypes.add(calculateType)
            }
        } else {
            provideNotFirstClearResult(
                isReverseResultFirstClear = isReverseResultFirstClear,
                reverseResultCount = reverseResultCount,
                directionResult = directionResult,
            ).forEach { calculateType ->
                calculateTypes.add(calculateType)
            }
        }
        return calculateTypes

    }

    private fun provideFirstClearResult(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        directionResult: DirectionResult,
    ): CalculateType? {
        return if (isThreeToThreeCount(directionResult.count + reverseResultCount)) {
            CalculateType.ThreeToThreeCount
        } else if (isFourToFourCount(directionResult.count + reverseResultCount)) {
            CalculateType.FourToFourCount
        } else if (isThreeToThreeCount(directionResult.count) &&
            isFourToFourCount(reverseResultCount) && !isReverseResultFirstClear
        ) {
            CalculateType.IsReverseTwoAndThree
        } else {
            null
        }
    }

    private fun provideNotFirstClearResult(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        directionResult: DirectionResult,
    ): List<CalculateType> {
        val calculateTypes: MutableList<CalculateType> = mutableListOf()
        getClearFourToFourResult(isReverseResultFirstClear, directionResult)?.let { calculateTypes.add(it) }
        if (calculateTypes.isEmpty()) {
            getClearFourToFourResult(directionResult, reverseResultCount)?.let { calculateTypes.add(it) }
            getThreeToThreeCountResult(reverseResultCount)?.let { calculateTypes.add(it) }
            getFourToFourCountResult(reverseResultCount)?.let { calculateTypes.add(it) }
        }
        return calculateTypes
    }

    private fun getClearFourToFourResult(
        isReverseResultFirstClear: Boolean,
        directionResult: DirectionResult,
    ): CalculateType? {
        return if (isReverseResultFirstClear && isThreeToThreeCount(directionResult.count)) CalculateType.IsClearFourToFourCount else null
    }

    private fun getClearFourToFourResult(
        directionResult: DirectionResult,
        reverseResultCount: Int
    ): CalculateType? {
        return if (isThreeToThreeCount(directionResult.count + reverseResultCount)) CalculateType.IsClearFourToFourCount else null
    }

    private fun getThreeToThreeCountResult(
        reverseResultCount: Int,
    ): CalculateType? {
        return if (isThreeToThreeCount(reverseResultCount)) CalculateType.ThreeToThreeCount else null
    }

    private fun getFourToFourCountResult(
        reverseResultCount: Int,
    ): CalculateType? {
        return if (isFourToFourCount(reverseResultCount)) CalculateType.FourToFourCount else null
    }

    private fun isLastClearResult(directionResult: DirectionResult): Boolean {
        return directionResult.isLastClear
    }

    private fun isNotFirstClearResult(directionResult: DirectionResult): Boolean {
        return !directionResult.isFirstClear
    }

    private fun isThreeToThreeCount(count: Int): Boolean {
        return count == Player.EDGE_THREE_TO_THREE_COUNT
    }

    private fun isFourToFourCount(count: Int): Boolean {
        return count == Player.EDGE_FOUR_TO_FOUR_COUNT
    }
}
