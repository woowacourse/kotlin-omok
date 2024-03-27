package omok.model.turn

import core.omok.rule.RanjuRule
import omok.adapter.RuleAdapter
import omok.model.ColumnStates
import omok.model.ErrorType.RanjuRuleException
import omok.model.state.Stone

class BlackTurn(
    override val stoneHistory: ArrayDeque<Stone> = ArrayDeque(),
    override val adapter: RuleAdapter = RuleAdapter(listOf(RanjuRule())),
) : Turn() {
    override fun isFinished(): Boolean = false

    override fun processTurn(
        stoneStates: List<ColumnStates>,
        row: Int,
        column: Int,
    ): Result<Turn> {
        adapter.convertToInteger(stoneStates)
        if (adapter.validPosition(row, column)) return Result.failure(RanjuRuleException())
        if (adapter.isGameWon(row, column)) return Result.success(FinishedTurn())
        return Result.success(turnOff())
    }
}
