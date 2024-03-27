package omock.model.player

import omock.model.position.Column
import omock.model.position.Row
import omock.model.rule.OMockRule
import omock.model.search.Direction
import omock.model.search.VisitedDirectionResult
import omock.model.stone.Stone

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
