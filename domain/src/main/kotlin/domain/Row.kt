package domain.domain

import domain.CoordinateState
import domain.domain.constant.DomainConstant

class Row {
    private val _row = MutableList(DomainConstant.BOARD_SIZE) { CoordinateState.EMPTY }
    val row: List<CoordinateState> get() = _row.toList()

    operator fun get(x: Int) = row[x]
    operator fun set(x: Int, value: CoordinateState) {
        _row[x] = value
    }
}
