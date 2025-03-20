package omok.model.stone

import omok.model.stone.position.Position

class Stone(
    val position: Position,
    val stoneState: StoneState,
) {
    constructor(lastStone: Pair<Position, StoneState>) : this(lastStone.first, lastStone.second)
}
