package omok.model.board

import omok.model.stone.StoneState

interface Board {
    val board: Map<Position, StoneState>
    val keys: Set<Position>
    val values: Collection<StoneState>
    val ySize: Int
    val xSize: Int

    fun canPlaceStone(position: Position): Boolean

    fun placeStone(
        position: Position,
        stoneState: StoneState,
    )

    fun stoneState(position: Position): StoneState

    fun isFull(): Boolean
}
