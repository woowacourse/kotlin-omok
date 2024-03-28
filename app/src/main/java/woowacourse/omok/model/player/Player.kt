package woowacourse.omok.model.player

import woowacourse.omok.model.position.Column
import woowacourse.omok.model.position.Row
import woowacourse.omok.model.rule.OMockRule
import woowacourse.omok.model.search.Direction
import woowacourse.omok.model.search.VisitedDirectionResult
import woowacourse.omok.model.stone.Stone

sealed class Player {
    abstract val stoneHistory: ArrayDeque<Stone>

    fun turn(action: () -> Pair<String, String>): Stone {
        val coordinate = action()
        val row = Row(coordinate.second)
        val column = Column(coordinate.first)
        return generateStone(row, column)
    }

    private fun generateStone(
        row: Row,
        column: Column,
    ): Stone {
        return Stone.from(row = row, column = column)
    }

    fun judgementResult(visitedDirectionResult: VisitedDirectionResult): Boolean {
        visitedDirectionResult.visited.entries.forEach { (key, result) ->
            val reverseResultCount: Int =
                visitedDirectionResult.visited[Direction.reverse(key)]?.count ?: OMockRule.MIN_REVERSE_COUNT
            if (isOverMinOMockCount(reverseResultCount + result.count)) return true
        }
        return false
    }

    private fun isOverMinOMockCount(count: Int): Boolean {
        return count >= OMockRule.MIN_O_MOCK_COUNT
    }
}
