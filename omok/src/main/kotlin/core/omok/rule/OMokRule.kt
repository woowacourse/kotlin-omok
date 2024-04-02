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
        private const val MIN_O_MOCK_COUNT = 4

        fun isGameWon(
            stoneStates: List<List<Int>>,
            row: Int,
            column: Int,
        ): Boolean {
            val visited = OMokSearch.loadMap(stoneStates, row, column)
            var oMokResults = 0

            visited.entries.forEach { (key, result) ->
                visited[Direction.reverse(key)]?.let { reverseResult ->
                    if (result.count >= MIN_O_MOCK_COUNT) return true
                    if (!result.isFirstClear && !reverseResult.isFirstClear) {
                        oMokResults = maxOf(oMokResults, reverseResult.count + result.count)
                    }
                }
            }
            return oMokResults >= MIN_O_MOCK_COUNT
        }
    }
}
