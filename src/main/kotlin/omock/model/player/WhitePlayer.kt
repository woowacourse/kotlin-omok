package omock.model.player

import omock.model.rule.OMockRule
import omock.model.search.Direction
import omock.model.search.VisitedDirectionResult
import omock.model.stone.Stone

data class WhitePlayer(
    override val stoneHistory: ArrayDeque<Stone> = ArrayDeque(),
) : Player() {
    override fun judgementResult(visitedDirectionResult: VisitedDirectionResult): Boolean {
        visitedDirectionResult.visited.entries.forEach { (key, result) ->
            val reverseResultCount: Int =
                visitedDirectionResult.visited[Direction.reverse(key)]?.count ?: OMockRule.MIN_REVERSE_COUNT
            return reverseResultCount + result.count >= OMockRule.MIN_O_MOCK_COUNT
        }
        return false
    }
}
