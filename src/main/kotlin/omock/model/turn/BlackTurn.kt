package omock.model.turn

import core.omock.rule.FourToFourRule
import core.omock.rule.IsClearFourToFourRule
import core.omock.rule.IsReverseTwoAndThreeRule
import core.omock.rule.ThreeToThreeRule
import omock.adapter.RuleAdapter
import omock.model.ColumnStates
import omock.model.ErrorType.RanjuRuleException
import omock.model.state.Stone

class BlackTurn(
    override val stoneHistory: ArrayDeque<Stone> = ArrayDeque(),
    override val adapter: RuleAdapter =
        RuleAdapter(
            listOf(
                FourToFourRule(),
                IsClearFourToFourRule(),
                IsReverseTwoAndThreeRule(),
                ThreeToThreeRule(),
            ),
        ),
) : Turn() {
    override fun isFinished(): Boolean = false

    override fun processTurn(
        stoneStates: List<ColumnStates>,
        row: Int,
        column: Int,
    ): Result<Turn> {
        if (adapter.convertToInteger(stoneStates)
                .validPosition(row, column)
        ) {
            return Result.failure(RanjuRuleException())
        }
        if (adapter.isGameWon(row, column)) return Result.success(FinishedTurn())
        return Result.success(turnOff())
    }
}
