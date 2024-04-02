package woowacourse.omok.domain.omok.model.state

import woowacourse.omok.domain.omok.model.Position

class White : TurnState() {
    override fun addStone(
        position: Position,
        placeStone: (Position) -> Unit,
    ) {
        placeStone(position)
    }
}
