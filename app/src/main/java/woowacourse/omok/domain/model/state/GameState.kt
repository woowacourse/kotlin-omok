package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StonePosition

sealed interface GameState {
    fun place(
        board: Board,
        position: Position,
    ): GameState

    fun latestStone(): Stone

    fun latestPosition(): Position?

    fun running(): Boolean

    fun invalidPosition(): Boolean

    fun finished(): Boolean

    fun handleInvalidPosition(handling: (StonePosition, String) -> Unit): GameState
}
