package omock.model.turn

import omock.adapter.RuleAdapter
import omock.model.ColumnStates
import omock.model.state.Stone

class WhiteTurn(
    override val stoneHistory: ArrayDeque<Stone> = ArrayDeque(),
    override val adapter: RuleAdapter = RuleAdapter(),
) : Turn() {
    override fun isFinished(): Boolean = false

    override fun processTurn(
        stoneStates: List<ColumnStates>,
        row: Int,
        column: Int,
    ): Result<Turn> {
        if (adapter.convertToInteger(stoneStates).isGameWon(row, column)) return Result.success(FinishedTurn())
        return Result.success(turnOff())
    }
}
