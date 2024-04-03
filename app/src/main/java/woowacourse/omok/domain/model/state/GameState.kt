package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.StonePosition

sealed interface GameState {
    fun place(
        board: Board,
        position: Position,
    ): GameState

    fun latestStonePosition(): StonePosition

    fun running(): Boolean

    fun invalidPosition(): Boolean

    fun finished(): Boolean

    fun handleInvalidPosition(handling: (StonePosition, String) -> Unit): GameState
}
