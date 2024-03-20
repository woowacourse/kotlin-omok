package omock.model

data object WhitePlayer : Player {
    override fun judgementResult(visited: Map<Direction, Result>): Boolean {
        visited.entries.forEach { (key, result) ->
            val reverseResultCount: Int = visited[Direction.reverse(key)]?.count ?: Player.MIN_REVERSE_COUNT
            return reverseResultCount + result.count >= Player.MIN_O_MOCK_COUNT
        }
        return false
    }
}
