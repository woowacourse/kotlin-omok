package core.omok.rule

import core.omok.Direction
import core.omok.serach.OMokSearch

interface OMokRule {
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
            val visited = OMokSearch.loadMap(stoneStates, row, column)
            val oMokResults = mutableListOf<Int>()
            visited.entries.forEach { (key, result) ->
                val reverseResultCount: Int =
                    visited[Direction.reverse(key)]?.count ?: MIN_REVERSE_COUNT
                oMokResults.add(reverseResultCount + result.count)
            }
            return oMokResults.max() >= MIN_O_MOCK_COUNT
        }
    }
}
