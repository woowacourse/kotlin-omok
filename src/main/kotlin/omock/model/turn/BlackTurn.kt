package omock.model.turn

import omock.model.Direction
import omock.model.Result
import omock.model.rule.BlackRule
import omock.model.rule.OMockRule
import omock.model.state.Stone

class BlackTurn(
    override val stoneHistory: ArrayDeque<Stone> = ArrayDeque(),
    override val oMockRule: OMockRule = BlackRule(),
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
