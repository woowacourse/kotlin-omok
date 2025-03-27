package woowacourse.omok.domain.model.position

import woowacourse.omok.domain.model.stone.StoneType

data class Stone(
    val position: Position,
    val stoneType: StoneType,
) {
    constructor(column: Int, row: Int, size: Int, stoneType: StoneType) : this(
        Position(column, row, size),
        stoneType,
    )
}
