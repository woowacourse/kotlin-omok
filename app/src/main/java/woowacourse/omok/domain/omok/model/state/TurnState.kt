package woowacourse.omok.domain.omok.model.state

import woowacourse.omok.domain.omok.model.Position

abstract class TurnState {
    abstract fun addStone(
        position: Position,
        placeStone: (Position) -> Unit,
    )
}
