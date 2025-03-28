package woowacourse.omok.model

import woowacourse.omok.model.position.Col
import woowacourse.omok.model.position.Row

class Board(
    val col: Col = Col(DEFAULT_COL_SIZE),
    val row: Row = Row(DEFAULT_ROW_SIZE),
) {
    private val _stones: MutableSet<Stone> = mutableSetOf()
    val stones: Set<Stone> get() = _stones.toSet()

    fun add(newStone: Stone): MoveResult {
        if (newStone.position.x.value !in 1..col.value) return MoveResult.Failure.StoneNotWithinColumn
        if (newStone.position.y.value !in 1..row.value) return MoveResult.Failure.StoneNotWithinRow
        _stones.add(newStone)
        return MoveResult.Success.Playing
    }

    fun filterStones(color: Color): List<Stone> = _stones.filter { stone -> stone.color == color }

    companion object {
        private const val DEFAULT_COL_SIZE = 15
        private const val DEFAULT_ROW_SIZE = 15
    }
}
