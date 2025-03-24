package omok.model.board

import omok.model.stone.StoneState

interface Board {
    val board: Map<Position, StoneState>

    fun canPlaceStone(position: Position): Boolean

    fun placeStone(
        position: Position,
        stoneState: StoneState,
    ): Board

    fun stoneState(position: Position): StoneState

    fun isFull(): Boolean
}
