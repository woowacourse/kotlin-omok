package omock.model

data class WhitePlayer(
    override val stoneHistory: ArrayDeque<Stone> = ArrayDeque(),
) : Player() {
    override fun judgementResult(visited: Map<Direction, Result>): Boolean {
        visited.entries.forEach { (key, result) ->
            val reverseResultCount: Int = visited[Direction.reverse(key)]?.count ?: MIN_REVERSE_COUNT
            return reverseResultCount + result.count >= MIN_O_MOCK_COUNT
        }
        return false
    }
}
