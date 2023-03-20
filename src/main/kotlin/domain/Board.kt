package domain

import domain.CoordinateState.EMPTY
import domain.rule.ExactlyFive
import domain.rule.ExceedFive
import domain.rule.ForbiddenFour
import domain.rule.ForbiddenThree

class Board(
    value: List<MutableList<CoordinateState>> = List(BOARD_SIZE) { MutableList(BOARD_SIZE) { EMPTY } },
) {

    private val _value = value.map { it.toMutableList() }.toList()
    val value: List<List<CoordinateState>> get() = _value
    var lastPosition: Position? = null
        private set

    fun addStone(coordinateState: CoordinateState, position: Position) {
        _value[position.getY()][position.getX()] = coordinateState
        lastPosition = position
    }

    fun isEmpty(position: Position): Boolean {
        return value[position.getY()][position.getX()] == EMPTY
    }

    fun isForbiddenThree(position: Position): Boolean = ForbiddenThree.isForbiddenThree(value, position)
    fun isForbiddenFour(position: Position): Boolean = ForbiddenFour.isForbiddenFour(value, position)
    fun isExceedFive(position: Position, coordinateState: CoordinateState): Boolean =
        ExceedFive.isExceedFive(value, position, coordinateState)

    fun isExactlyFive(position: Position, coordinateState: CoordinateState): Boolean =
        ExactlyFive.isExactlyFive(value, position, coordinateState)

    companion object {
        const val BOARD_SIZE = 15
    }
}
