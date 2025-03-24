package omok.domain.model.position

import omok.domain.model.stone.StoneType

data class Stone(
    val position: Position,
    val stoneType: StoneType,
) {
    constructor(column: Int, inColumnRange: Boolean, row: Int, inRowRange: Boolean, stoneType: StoneType) : this(
        Position(column, inColumnRange, row, inRowRange),
        stoneType,
    )
}
