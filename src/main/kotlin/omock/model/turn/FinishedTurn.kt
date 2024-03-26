package omock.model.turn

import omock.model.state.Stone

class FinishedTurn(
    override val stoneHistory: ArrayDeque<Stone> = ArrayDeque(),
) : Turn() {
    override fun isFinished(): Boolean = true

    override fun processTurn(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Result<Turn> {
        throw IllegalArgumentException(ERROR_FINISHED_MESSAGE)
    }

    companion object {
        const val ERROR_FINISHED_MESSAGE = "게임이 이미 종료되었습니다"
    }
}
