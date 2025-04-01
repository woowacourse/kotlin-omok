package woowacourse.omok.data

import omok.model.entity.Stone
import omok.model.entity.position.GridElement

data class History(
    val turn: String,
    val row: Int,
    val column: Int,
) {
    constructor(turn: Stone, row: GridElement, column: GridElement) : this(
        turn.name,
        row.value,
        column.value,
    )
}
