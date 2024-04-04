package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.controller.InvalidPositionHandler
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StonePosition

sealed class InvalidPositionState(
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

    override fun handleInvalidPosition(handler: InvalidPositionHandler): GameState {
        handler.handle(latestStonePosition, this)
        return latestState
    }
}

class AlreadyHaveStone(latestStonePosition: StonePosition, latestState: GameState) :
    InvalidPositionState(latestStonePosition, latestState)

class ForbiddenPosition(latestStonePosition: StonePosition, latestState: GameState) :
    InvalidPositionState(latestStonePosition, latestState)
