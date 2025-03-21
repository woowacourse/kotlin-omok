package omok.model.board

import omok.model.stone.StoneState

data class Position(
    val x: X,
    val y: Y,
    val stoneState: StoneState = StoneState.NONE,
) {
    //
}
