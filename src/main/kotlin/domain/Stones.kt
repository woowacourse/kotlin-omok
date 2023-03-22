package domain.domain

import domain.CoordinateState
import domain.CoordinateState.EMPTY
import domain.Position
import domain.domain.constant.DomainConstant.BOARD_SIZE

class Stones {
    private val _stones = MutableList(BOARD_SIZE) { Row() }
    val stones: List<Row> get() = _stones
    var lastPosition: Position? = null
        private set

    fun addStone(position: Position, color: CoordinateState) {
        if (color == EMPTY) throw IllegalArgumentException(NOT_EMPTY)
        _stones[position.y][position.x] = color
        lastPosition = position
    }

    operator fun get(y: Int, x: Int) = _stones[y][x]

    companion object {
        const val NOT_EMPTY = "color가 아닙니다. EMPTY가 들어왔습니다."
    }
}

class Row {
    private val _row = MutableList(BOARD_SIZE) { EMPTY }
    val row: List<CoordinateState> get() = _row.toList()

    operator fun get(x: Int) = row[x]
    operator fun set(x: Int, value: CoordinateState) {
        _row[x] = value
    }
}
