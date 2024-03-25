package core.omock.rule

import core.omock.Direction
import core.omock.Result
import core.omock.serach.OMockSearchImpl

class OMockRuleImpl : OMockRule() {
    private val oMock = OMockSearchImpl()

    override fun threeToThreeCount(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Boolean {
        val visited = oMock.loadMap(stoneStates, row, column)
        var threeToThreeCount = INIT_COUNT

        visited.entries.forEach { (key, result) ->
            val isReverseResultFirstClear: Boolean = visited[Direction.reverse(key)]?.isFirstClear ?: false
            val reverseResultCount: Int = visited[Direction.reverse(key)]?.count ?: MIN_REVERSE_COUNT
            if (getThreeToThreeCalculateType(isReverseResultFirstClear, reverseResultCount, result)) {
                if (++threeToThreeCount >= MIN_THREE_TO_THREE_COUNT) return true
            }
        }
        return false
    }

    override fun fourToFourCount(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Boolean {
        val visited = oMock.loadMap(stoneStates, row, column)
        var fourToFourCount = INIT_COUNT

        visited.entries.forEach { (key, result) ->
            val isReverseResultFirstClear: Boolean = visited[Direction.reverse(key)]?.isFirstClear ?: false
            val reverseResultCount: Int = visited[Direction.reverse(key)]?.count ?: MIN_REVERSE_COUNT

            if (getFourToFourCalculateType(isReverseResultFirstClear, reverseResultCount, result)) {
                if (++fourToFourCount >= MIN_FOUR_TO_FOUR_COUNT) return true
            }
        }
        return false
    }

    override fun isClearFourToFour(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Boolean {
        val visited = oMock.loadMap(stoneStates, row, column)
        var isClearFourToFourCount = INIT_COUNT

        visited.entries.forEach { (key, result) ->
            val isReverseResultFirstClear: Boolean = visited[Direction.reverse(key)]?.isFirstClear ?: false
            val reverseResultCount: Int = visited[Direction.reverse(key)]?.count ?: MIN_REVERSE_COUNT

            if (getClearFourToFourCalculateType(isReverseResultFirstClear, reverseResultCount, result)) {
                if (++isClearFourToFourCount >= MIN_IS_CLEAR_FOUR_TO_FOUR_COUNT) return true
            }
        }
        return false
    }

    override fun isReverseTwoAndThree(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Boolean {
        val visited = oMock.loadMap(stoneStates, row, column)

        visited.entries.forEach { (key, result) ->
            val isReverseResultFirstClear: Boolean = visited[Direction.reverse(key)]?.isFirstClear ?: false
            val reverseResultCount: Int = visited[Direction.reverse(key)]?.count ?: MIN_REVERSE_COUNT

            if (getReverseTwoAndThreeCalculateType(isReverseResultFirstClear, reverseResultCount, result)) {
                return true
            }
        }
        return false
    }

    override fun isGameWon(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Boolean {
        val visited = oMock.loadMap(stoneStates, row, column)

        visited.entries.forEach { (key, result) ->
            val reverseResultCount: Int = visited[Direction.reverse(key)]?.count ?: MIN_REVERSE_COUNT
            return reverseResultCount + result.count >= MIN_O_MOCK_COUNT
        }
        return false
    }

    private fun getThreeToThreeCalculateType(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        result: Result,
    ): Boolean {
        if (!result.isLastClear) return false
        if (!result.isFirstClear) {
            if (result.count + reverseResultCount == EDGE_THREE_TO_THREE_COUNT) return true
        } else {
            if (isReverseResultFirstClear && result.count == EDGE_THREE_TO_THREE_COUNT) return false
            if (reverseResultCount == EDGE_THREE_TO_THREE_COUNT) return true
        }
        return false
    }

    private fun getFourToFourCalculateType(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        result: Result,
    ): Boolean {
        if (!result.isLastClear) return false
        if (!result.isFirstClear) {
            if (result.count + reverseResultCount == EDGE_FOUR_TO_FOUR_COUNT) return true
        } else {
            if (isReverseResultFirstClear && result.count == EDGE_THREE_TO_THREE_COUNT) return false
            if (reverseResultCount == EDGE_FOUR_TO_FOUR_COUNT) return true
        }
        return false
    }

    private fun getClearFourToFourCalculateType(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        result: Result,
    ): Boolean {
        if (!result.isLastClear) return false
        if (!result.isFirstClear) return false
        if (isReverseResultFirstClear && result.count == EDGE_THREE_TO_THREE_COUNT) {
            return true
        } else {
            if (result.count + reverseResultCount == EDGE_THREE_TO_THREE_COUNT) return true
        }
        return false
    }

    private fun getReverseTwoAndThreeCalculateType(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        result: Result,
    ): Boolean {
        if (!result.isLastClear) return false
        if (result.count == EDGE_THREE_TO_THREE_COUNT && reverseResultCount == EDGE_FOUR_TO_FOUR_COUNT && !isReverseResultFirstClear) {
            return true
        }
        return false
    }

    companion object {
        const val INIT_COUNT = 0
        const val MIN_FOUR_TO_FOUR_COUNT = 4
        const val MIN_IS_CLEAR_FOUR_TO_FOUR_COUNT = 4
        const val MIN_THREE_TO_THREE_COUNT = 4
        const val MIN_REVERSE_COUNT = 0
        const val MIN_O_MOCK_COUNT = 4
        const val EDGE_THREE_TO_THREE_COUNT = 2
        const val EDGE_FOUR_TO_FOUR_COUNT = 3
    }
}
