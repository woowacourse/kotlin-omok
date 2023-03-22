package woowacourse.omok

import domain.CoordinateState
import domain.CoordinateState.BLACK
import domain.CoordinateState.EMPTY
import domain.CoordinateState.WHITE
import domain.Position
import domain.domain.Row
import domain.domain.constant.DomainConstant.BOARD_SIZE
import domain.domain.state.State

object OmokGameMapper {
    private const val BLACK_TEXT = "흑"
    private const val WHITE_TEXT = "백"

    fun indexToPosition(index: Int): Position {
        val y = index / BOARD_SIZE
        val x = index % BOARD_SIZE
        return Position(BOARD_SIZE - 1 - y, x)
    }

    fun CoordinateState.toName(): String {
        return if (this == BLACK) BLACK_TEXT else WHITE_TEXT
    }

    fun matchColor(color: CoordinateState): Int? {
        return when (color) {
            BLACK -> R.drawable.black_stone
            WHITE -> R.drawable.white_stone
            else -> null
        }
    }

    fun mapStones(state: State): List<CoordinateState> {
        val stones = MutableList(BOARD_SIZE * BOARD_SIZE) { EMPTY }
        state.stones.stones.forEachIndexed { y, row -> loopRow(y, row, stones) }
        return stones.toList()
    }

    private fun loopRow(y: Int, row: Row, stones: MutableList<CoordinateState>) {
        row.row.forEachIndexed { x, coordinateState ->
            if (coordinateState == EMPTY) return@forEachIndexed
            val index = positionToIndex(Position(y, x))
            stones[index] = coordinateState
        }
    }

    private fun positionToIndex(position: Position): Int {
        return (BOARD_SIZE - 1 - position.y) * BOARD_SIZE + position.x
    }
}
