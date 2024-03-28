package woowacourse.omok.view

import omok.model.Position
import omok.model.state.GameState

interface OmokView {
    fun updateBoard(
        position: Position,
        gameState: GameState,
    )
}
