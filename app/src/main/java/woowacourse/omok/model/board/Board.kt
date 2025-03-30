package woowacourse.omok.model.board

import woowacourse.omok.model.stone.StoneState

interface Board {
    val board: Map<Position, StoneState>

    fun canPlaceStone(position: Position): Boolean

    fun placeStone(
        position: Position,
        stoneState: StoneState,
    )

    fun stoneState(position: Position): StoneState

    fun stoneState(
        x: Int,
        y: Int,
    ): StoneState
}
