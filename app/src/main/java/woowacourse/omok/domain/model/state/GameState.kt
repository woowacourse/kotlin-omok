package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.StonePosition

sealed interface GameState {
    fun place(
        board: Board,
        position: Position,
    ): GameState

    fun handleInvalidPosition(handling: (StonePosition, String) -> Unit): GameState
}
