package omock.model.turn

import omock.model.Direction
import omock.model.Result
import omock.model.Stone
import omock.model.rule.BlackRule
import omock.model.rule.OMockRule
import java.lang.IllegalArgumentException

class FinishedTurn(
    override val stoneHistory: ArrayDeque<Stone> = ArrayDeque(),
    override val oMockRule: OMockRule = BlackRule(),
) : Turn() {
    override fun isFinished(): Boolean = true

    override fun judgementResult(visited: Map<Direction, Result>): Turn {
        throw IllegalArgumentException(ERROR_FINISHED_MESSAGE)
    }

    companion object {
        const val ERROR_FINISHED_MESSAGE = "게임이 이미 종료되었습니다"
    }
}
