package core.omok.rule

import core.omok.Direction
import core.omok.OMokResult
import core.omok.serach.OMokSearch

class RanjuRule : OMokRule {
    override fun validPosition(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Boolean {
        return listOf(
            threeToThreeCount(stoneStates, row, column),
            fourToFourCount(stoneStates, row, column),
            isClearFourToFour(stoneStates, row, column),
            isReverseTwoAndThree(stoneStates, row, column),
        ).any { it }
    }

    private fun threeToThreeCount(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Boolean {
        val visited = OMokSearch.loadMap(stoneStates, row, column)
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

    private fun fourToFourCount(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Boolean {
        val visited = OMokSearch.loadMap(stoneStates, row, column)
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

    private fun isClearFourToFour(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Boolean {
        val visited = OMokSearch.loadMap(stoneStates, row, column)
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

    private fun isReverseTwoAndThree(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Boolean {
        val visited = OMokSearch.loadMap(stoneStates, row, column)

        visited.entries.forEach { (key, result) ->
            val isReverseResultFirstClear: Boolean = visited[Direction.reverse(key)]?.isFirstClear ?: false
            val reverseResultCount: Int = visited[Direction.reverse(key)]?.count ?: MIN_REVERSE_COUNT

            if (getReverseTwoAndThreeCalculateType(isReverseResultFirstClear, reverseResultCount, result)) {
                return true
            }
        }
        return false
    }

    private fun getThreeToThreeCalculateType(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        oMokResult: OMokResult,
    ): Boolean {
        if (!oMokResult.isLastClear) return false
        if (!oMokResult.isFirstClear) {
            if (oMokResult.count + reverseResultCount == EDGE_THREE_TO_THREE_COUNT) return true
        } else {
            if (isReverseResultFirstClear && oMokResult.count == EDGE_THREE_TO_THREE_COUNT) return false
            if (reverseResultCount == EDGE_THREE_TO_THREE_COUNT) return true
        }
        return false
    }

    private fun getFourToFourCalculateType(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        oMokResult: OMokResult,
    ): Boolean {
        if (!oMokResult.isLastClear) return false
        if (!oMokResult.isFirstClear) {
            if (oMokResult.count + reverseResultCount == EDGE_FOUR_TO_FOUR_COUNT) return true
        } else {
            if (isReverseResultFirstClear && oMokResult.count == EDGE_THREE_TO_THREE_COUNT) return false
            if (reverseResultCount == EDGE_FOUR_TO_FOUR_COUNT) return true
        }
        return false
    }

    private fun getClearFourToFourCalculateType(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        oMokResult: OMokResult,
    ): Boolean {
        if (!oMokResult.isLastClear) return false
        if (!oMokResult.isFirstClear) return false
        if (isReverseResultFirstClear && oMokResult.count == EDGE_THREE_TO_THREE_COUNT) {
            return true
        } else {
            if (oMokResult.count + reverseResultCount == EDGE_THREE_TO_THREE_COUNT) return true
        }
        return false
    }

    private fun getReverseTwoAndThreeCalculateType(
        isReverseResultFirstClear: Boolean,
        reverseResultCount: Int,
        oMokResult: OMokResult,
    ): Boolean {
        if (!oMokResult.isLastClear) return false
        if (oMokResult.count == EDGE_THREE_TO_THREE_COUNT && reverseResultCount == EDGE_FOUR_TO_FOUR_COUNT && !isReverseResultFirstClear) {
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
