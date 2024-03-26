package core.omock.rule

import core.omock.Direction
import core.omock.Result
import core.omock.serach.OMockSearch

class IsReverseTwoAndThreeRule : OMockRule {
    override fun validPosition(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Boolean {
        return isReverseTwoAndThree(stoneStates, row, column)
    }

    private fun isReverseTwoAndThree(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Boolean {
        val visited = OMockSearch.loadMap(stoneStates, row, column)

        visited.entries.forEach { (key, result) ->
            val isReverseResultFirstClear: Boolean = visited[Direction.reverse(key)]?.isFirstClear ?: false
            val reverseResultCount: Int = visited[Direction.reverse(key)]?.count ?: MIN_REVERSE_COUNT

            if (getReverseTwoAndThreeCalculateType(isReverseResultFirstClear, reverseResultCount, result)) {
                return true
            }
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
        const val MIN_REVERSE_COUNT = 0
        const val EDGE_THREE_TO_THREE_COUNT = 2
        const val EDGE_FOUR_TO_FOUR_COUNT = 3
    }
}
