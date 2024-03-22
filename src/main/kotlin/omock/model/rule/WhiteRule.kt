package omock.model.rule

import omock.model.Direction
import omock.model.Result
import omock.model.rule.OMockRule.Companion.MIN_O_MOCK_COUNT
import omock.model.rule.OMockRule.Companion.MIN_REVERSE_COUNT

class WhiteRule : OMockRule {
    override fun judgementResult(visited: Map<Direction, Result>): Boolean {
        visited.entries.forEach { (key, result) ->
            val reverseResultCount: Int = visited[Direction.reverse(key)]?.count ?: MIN_REVERSE_COUNT
            return reverseResultCount + result.count >= MIN_O_MOCK_COUNT
        }
        return false
    }
}
