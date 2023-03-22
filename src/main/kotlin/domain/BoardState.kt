package domain.domain

import domain.Board
import domain.CoordinateState
import domain.Position

class BoardState(
    value: List<MutableList<CoordinateState>> = List(Board.BOARD_SIZE) { MutableList(Board.BOARD_SIZE) { CoordinateState.EMPTY } },
) {
    private val _value = value.map { it.toMutableList() }.toList()
    val value: List<List<CoordinateState>> get() = _value

    fun addStone(coordinateState: CoordinateState, position: Position) {
        _value[position.coordinateY][position.coordinateX] = coordinateState
    }

    fun isEmpty(position: Position): Boolean = value[position.coordinateY][position.coordinateX] == CoordinateState.EMPTY

}
