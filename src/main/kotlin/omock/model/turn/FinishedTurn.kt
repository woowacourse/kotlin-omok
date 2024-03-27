package omock.model.turn

import omock.adapter.RuleAdapter
import omock.model.ColumnStates
import omock.model.state.Stone

class FinishedTurn(
    override val stoneHistory: ArrayDeque<Stone> = ArrayDeque(),
    override val adapter: RuleAdapter = RuleAdapter(),
) : Turn() {
    override fun isFinished(): Boolean = true

    override fun processTurn(
        stoneStates: List<ColumnStates>,
        row: Int,
        column: Int,
    ): Result<Turn> {
        throw IllegalArgumentException(ERROR_FINISHED_MESSAGE)
    }

    companion object {
        const val ERROR_FINISHED_MESSAGE = "게임이 이미 종료되었습니다"
    }
}
