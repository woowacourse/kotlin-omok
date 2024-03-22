package omock.model

import omock.model.rule.BlackRule
import omock.model.rule.OMockRule

class BlackPlayer(
    override val stoneHistory: ArrayDeque<Stone> = ArrayDeque(),
    override val oMockRule: OMockRule = BlackRule(),
) : Player() {
    override fun judgementResult(visited: Map<Direction, Result>): Boolean {
        return oMockRule.judgementResult(visited = visited)
    }
}
