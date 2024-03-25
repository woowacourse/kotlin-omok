package omock.model.player

import omock.model.search.Direction
import omock.model.search.DirectionResult
import omock.model.stone.Stone

data class WhitePlayer(
    override val stoneHistory: ArrayDeque<Stone> = ArrayDeque(),
) : Player() {
    override fun judgementResult(visited: Map<Direction, DirectionResult>): Boolean {
        visited.entries.forEach { (key, result) ->
            val reverseResultCount: Int = visited[Direction.reverse(key)]?.count ?: MIN_REVERSE_COUNT
            return reverseResultCount + result.count >= MIN_O_MOCK_COUNT
        }
        return false
    }
}
