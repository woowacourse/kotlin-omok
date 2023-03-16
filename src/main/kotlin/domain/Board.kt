package domain

import domain.CoordinateState.EMPTY
import domain.constant.Constant.BOARD_SIZE
import domain.rule.ExactlyFive
import domain.rule.ExceedFive
import domain.rule.ForbiddenFour
import domain.rule.ForbiddenThree

class Board(
    private val _board: List<MutableList<CoordinateState>> =
        List(BOARD_SIZE) { MutableList(BOARD_SIZE) { EMPTY } },
) {
    val board: List<List<CoordinateState>> get() = _board
    var lastPosition: Position? = null
        private set

    fun addStone(coordinateState: CoordinateState, position: Position) {
        _board[position.getY()][position.getX()] = coordinateState
        lastPosition = position
    }

    fun isEmpty(position: Position): Boolean {
        return board[position.getY()][position.getX()] == EMPTY
    }

    fun isForbiddenThree(position: Position): Boolean = ForbiddenThree.isForbiddenThree(board, position)
    fun isForbiddenFour(position: Position): Boolean = ForbiddenFour.isForbiddenFour(board, position)
    fun isExceedFive(position: Position, coordinateState: CoordinateState): Boolean =
        ExceedFive.isExceedFive(board, position, coordinateState)

    fun isExactlyFive(position: Position, coordinateState: CoordinateState): Boolean =
        ExactlyFive.isExactlyFive(board, position, coordinateState)
}
