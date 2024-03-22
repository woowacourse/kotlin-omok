package omock.model

import omock.model.rule.OMockRule
import omock.model.rule.WhiteRule

class WhitePlayer(
    override val stoneHistory: ArrayDeque<Stone> = ArrayDeque(),
    override val oMockRule: OMockRule = WhiteRule(),
) : Player() {
    override fun judgementResult(visited: Map<Direction, Result>): Boolean {
        return oMockRule.judgementResult(visited)
    }
}
