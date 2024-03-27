package core.omock.rule

import core.omock.Direction
import core.omock.serach.OMockSearch

interface OMockRule {
    fun validPosition(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Boolean

    companion object {
        private const val MIN_REVERSE_COUNT = 0
        private const val MIN_O_MOCK_COUNT = 4

        fun isGameWon(
            stoneStates: List<List<Int>>,
            row: Int,
            column: Int,
        ): Boolean {
            val visited = OMockSearch.loadMap(stoneStates, row, column)

            visited.entries.forEach { (key, result) ->
                val reverseResultCount: Int = visited[Direction.reverse(key)]?.count ?: MIN_REVERSE_COUNT
                return reverseResultCount + result.count >= MIN_O_MOCK_COUNT
            }
            return false
        }
    }
}
