package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.controller.InvalidPositionHandler
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone

sealed interface GameState {
    fun place(
        board: Board,
        position: Position,
    ): GameState

    fun latestStone(): Stone

    fun latestPosition(): Position?

    fun finished(): Boolean

    fun handleInvalidPosition(handler: InvalidPositionHandler): GameState
}
