package omock.model.turn

import omock.model.Direction
import omock.model.Result
import omock.model.rule.OMockRule
import omock.model.rule.WhiteRule
import omock.model.state.Stone

class WhiteTurn(
    override val stoneHistory: ArrayDeque<Stone> = ArrayDeque(),
    override val oMockRule: OMockRule = WhiteRule(),
) : Turn() {
    override fun isFinished(): Boolean = false

    override fun judgementResult(visited: Map<Direction, Result>): Turn {
        return if (oMockRule.judgementResult(visited)) {
            FinishedTurn()
        } else {
            turnOff()
        }
    }
}
