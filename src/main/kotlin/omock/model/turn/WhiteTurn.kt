package omock.model.turn

import core.omock.rule.OMockRule
import omock.model.state.Stone

class WhiteTurn(
    override val stoneHistory: ArrayDeque<Stone> = ArrayDeque(),
) : Turn() {
    override fun isFinished(): Boolean = false

    override fun processTurn(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Turn {
        OMockRule.run {
            if (isGameWon(stoneStates, row, column)) {
                return FinishedTurn()
            }
            return turnOff()
        }
    }
}
