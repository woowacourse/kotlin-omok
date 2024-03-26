package core.omock.rule

import core.omock.Direction
import core.omock.Result
import core.omock.serach.OMockSearch

class ThreeToThreeRule : OMockRule {
    override fun validPosition(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Boolean {
        return threeToThreeCount(stoneStates, row, column)
    }

    private fun threeToThreeCount(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Boolean {
        val visited = OMockSearch.loadMap(stoneStates, row, column)
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

    companion object {
        const val INIT_COUNT = 0
        const val MIN_THREE_TO_THREE_COUNT = 4
        const val MIN_REVERSE_COUNT = 0
        const val EDGE_THREE_TO_THREE_COUNT = 2
    }
}
