package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StonePosition

sealed class InvalidPosition(
    private val latestStonePosition: StonePosition,
    private val latestState: GameState,
) : GameState {
    abstract val exceptionMessage: String

    override fun place(
        board: Board,
        position: Position,
    ): GameState {
        throw IllegalStateException(exceptionMessage)
    }

    override fun running(): Boolean = false

    override fun invalidPosition(): Boolean = true

    override fun finished(): Boolean = false

    override fun latestStone(): Stone = latestStonePosition.stone

    override fun latestPosition(): Position = latestStonePosition.position

    override fun handleInvalidPosition(handling: (StonePosition, String) -> Unit): GameState {
        handling(latestStonePosition, exceptionMessage)
        return latestState
    }
}

class AlreadyHaveStone(latestStonePosition: StonePosition, latestState: GameState) :
    InvalidPosition(latestStonePosition, latestState) {
    override val exceptionMessage: String
        get() = "이미 돌이 있는 위치입니다."
}

class ForbiddenPosition(latestStonePosition: StonePosition, latestState: GameState) :
    InvalidPosition(latestStonePosition, latestState) {
    override val exceptionMessage: String
        get() = "금수 규칙에 따라 돌을 둘 수 없는 위치입니다."
}
