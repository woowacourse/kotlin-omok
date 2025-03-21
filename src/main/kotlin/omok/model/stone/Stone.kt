package omok.model.stone

import omok.model.stone.position.Position

class Stone(
    val position: Position,
    val stoneColor: StoneColor,
) {
    constructor(lastStone: Pair<Position, StoneColor>) : this(lastStone.first, lastStone.second)
}
