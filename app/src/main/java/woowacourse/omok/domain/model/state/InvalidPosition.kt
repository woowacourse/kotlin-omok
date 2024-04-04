package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StonePosition

// TODO: 여기서 토스트 메시지나 콘솔 출력문을 표시하는데 도메인에 있어야 하나?
sealed class InvalidPosition(
    private val latestStonePosition: StonePosition,
    private val latestState: GameState,
) : GameState {

    override fun place(
        board: Board,
        position: Position,
    ): GameState {
        throw IllegalStateException()
    }

    override fun finished(): Boolean = false

    override fun latestStone(): Stone = latestStonePosition.stone

    override fun latestPosition(): Position = latestStonePosition.position

    override fun handleInvalidPosition(handling: (StonePosition, InvalidPosition) -> Unit): GameState {
        handling(latestStonePosition, this)
        return latestState
    }
}

class AlreadyHaveStone(latestStonePosition: StonePosition, latestState: GameState) :
    InvalidPosition(latestStonePosition, latestState)

class ForbiddenPosition(latestStonePosition: StonePosition, latestState: GameState) :
    InvalidPosition(latestStonePosition, latestState)
