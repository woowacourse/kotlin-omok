package omok.model.entity.board

import omok.model.entity.Stone
import omok.model.entity.position.Position

interface BoardPosition {
    val position: Position
    val state: BoardPositionState

    fun withStone(stone: Stone): BoardPosition
}

data class DefaultBoardPosition(
    override val position: Position,
    override val state: BoardPositionState = BoardPositionState.Empty,
) : BoardPosition {
    override fun withStone(stone: Stone): BoardPosition = copy(state = state.stateWithStone(stone))
}
