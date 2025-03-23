package omok.model.stone

import omok.model.stone.position.Position

data class Stone(
    val position: Position,
    val stoneState: StoneState,
)
