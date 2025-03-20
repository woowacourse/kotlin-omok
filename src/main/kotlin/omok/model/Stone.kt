package omok.model

class Stone(
    val position: Position,
    val stoneState: StoneState,
) {
    constructor(lastStone: Pair<Position, StoneState>) : this(lastStone.first, lastStone.second)
}
