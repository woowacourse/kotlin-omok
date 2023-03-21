package domain

import domain.CoordinateState.EMPTY
import domain.domain.GameRule

class Board(
    value: List<MutableList<CoordinateState>> = List(BOARD_SIZE) { MutableList(BOARD_SIZE) { EMPTY } },
    private val gameRule: GameRule,
) {

    private val _value = value.map { it.toMutableList() }.toList()
    val value: List<List<CoordinateState>> get() = _value
    var lastPosition: Position? = null
        private set

    fun addStone(coordinateState: CoordinateState, position: Position) {
        _value[position.coordinateY][position.coordinateX] = coordinateState
        lastPosition = position
    }

    fun isEmpty(position: Position): Boolean {
        return value[position.coordinateY][position.coordinateX] == EMPTY
    }

    fun isForbiddenThree(position: Position): Boolean = gameRule.isForbiddenThree(position, value)
    fun isForbiddenFour(position: Position): Boolean = gameRule.isForbiddenFour(position, value)
    fun isExceedFive(position: Position, coordinateState: CoordinateState): Boolean =
        gameRule.isExceedFive(position, coordinateState, value)

    fun isExactlyFive(position: Position, coordinateState: CoordinateState): Boolean =
        gameRule.isExactlyFive(position, coordinateState, value)

    companion object {
        const val BOARD_SIZE = 15
    }
}
